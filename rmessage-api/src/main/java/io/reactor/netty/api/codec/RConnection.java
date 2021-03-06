package io.reactor.netty.api.codec;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.NettyContext;
import reactor.ipc.netty.NettyInbound;
import reactor.ipc.netty.NettyOutbound;

import java.net.InetSocketAddress;


/**
 * @Auther: lxr
 * @Date: 2018/11/4 17:30
 * @Description:
 */
@Data
public class RConnection implements Connection {

    private NettyInbound inbound;

    private NettyOutbound outbound;

    private NettyContext context;




    public RConnection(NettyInbound inbound, NettyOutbound outbound, NettyContext context) {
        this.inbound = inbound;
        this.outbound = outbound;
        this.context = context;
    }

    @Override
    public Mono<Void> dispose() {
        return Mono.defer(()->{
            if(!context.isDisposed())
                context.dispose();
            return Mono.empty();
        });
    }



    @Override
    public Mono<NettyInbound> onReadIdle(Long l, Runnable readLe) {
        return   Mono.just(inbound.onReadIdle(l,readLe));
    }



    @Override
    public Mono<NettyOutbound> onWriteIdle(Long l, Runnable write) {
        return Mono.just(outbound.onWriteIdle(l,write));
    }

    @Override
    public Flux<TransportMessage> receiveMsg() {
        return inbound.receiveObject().cast(TransportMessage.class).map(message -> message.setConnection(this));
    }

    @Override
    public Mono<InetSocketAddress> address() {
        return Mono.just(context.address());
    }

    @Override
    public void onClose(Runnable close) {
        context.onClose(close);
    }
}
