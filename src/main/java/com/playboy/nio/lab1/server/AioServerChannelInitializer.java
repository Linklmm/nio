package com.playboy.nio.lab1.server;

import com.playboy.nio.lab1.ChannelInitializer;
import com.playboy.nio.lab1.IoConstant;
import com.playboy.nio.lab1.handler.AioServerHandler;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Aio Server端 初始化器
 */
public class AioServerChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(AsynchronousSocketChannel channel) throws Exception {
        channel.read(ByteBuffer.allocate(1024), 10, TimeUnit.SECONDS, null,
                new AioServerHandler(channel, Charset.forName(IoConstant.GBK)));
    }
}
