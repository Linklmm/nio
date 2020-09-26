package com.playboy.nio.lab1.handler;

import com.playboy.nio.lab1.adpter.ChannelAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

@Slf4j
public class AioServerHandler extends ChannelAdapter {
    public AioServerHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            log.error("链接报告信息：{}" + channelHandler.channel().getRemoteAddress());
            //通知客户端连接建立成功
            channelHandler.writeAndFlush("通知客户端连接建立成功:" + LocalDateTime.now() + " "
                    + channelHandler.channel().getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("AioServerHandler happen exception ! message:{}", e.getMessage());
        }
    }

    @Override
    public void channelNactive(ChannelHandler channelHandler) {

    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {

    }
}
