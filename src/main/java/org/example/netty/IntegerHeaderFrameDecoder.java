package org.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class IntegerHeaderFrameDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4){
            return;
        }

        // 包结构：4个字节packet长度，packet body
        in.markReaderIndex();  // 记录读位置
        int length = in.readInt(); // 获取包头中包的长度
        if(in.readableBytes() < length){ // 不是个完整包
            in.resetReaderIndex(); // 重置读位置
            return;
        }
        out.add(in.readBytes(length));
    }
}
