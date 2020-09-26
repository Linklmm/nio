package com.playboy.nio.lab1.handler;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * AIO - channel处理器
 */
public class ChannelHandler {
    private AsynchronousSocketChannel channel;
    private Charset charset;

    public ChannelHandler(AsynchronousSocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg){
        byte[] bytes = msg.toString().getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer);
    }
    public AsynchronousSocketChannel channel(){
        return channel;
    }

    public void setChannel(AsynchronousSocketChannel channel){
        this.channel = channel;
    }
}
