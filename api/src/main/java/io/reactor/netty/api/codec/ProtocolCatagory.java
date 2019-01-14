package io.reactor.netty.api.codec;

/**
 * @Auther: lxr
 * @Date: 2018/12/19 14:25
 * @Description:
 */
public enum  ProtocolCatagory {
    ONLINE((byte)0),//在线
    ONE((byte)1),   // 单发
    GROUP((byte)2),// 群发
    ACCEPT((byte)3),// 客户端接受消息
    ONEACK((byte)10),
    GROUPACK((byte)11),
    JOIN((byte)12), //加入群组
    LEAVE((byte)13),//离开群组
    PING((byte)14),  //心跳
    PONG((byte)15) //回复
    ;
    private byte number;

    ProtocolCatagory(byte i) {
        this.number=i;
    }

}
