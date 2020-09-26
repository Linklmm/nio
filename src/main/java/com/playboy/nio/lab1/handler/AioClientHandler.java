package com.playboy.nio.lab1.handler;

import com.playboy.nio.lab1.adpter.ChannelAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.Date;

@Slf4j
public class AioClientHandler extends ChannelAdapter {
    public AioClientHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            log.error("客户端连接建立成功：" + channelHandler.channel().getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("aio client happen exception！");
        }
    }

    @Override
    public void channelNactive(ChannelHandler channelHandler) {

    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        log.error("服务端接收到：" + new Date() + "" + msg + "\r\n");
        channelHandler.writeAndFlush("客户端信息处理success ！\r\n");
    }
}
