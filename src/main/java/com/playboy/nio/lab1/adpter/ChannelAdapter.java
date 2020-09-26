package com.playboy.nio.lab1.adpter;

import com.playboy.nio.lab1.handler.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * channel适配器
 */
@Slf4j
public abstract class ChannelAdapter implements CompletionHandler<Integer,Object> {
    private AsynchronousSocketChannel channel;
    private Charset charset;

    public ChannelAdapter(AsynchronousSocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
        if (channel.isOpen()){
            channelActive(new ChannelHandler(channel,charset));
        }
    }

    public abstract void channelActive(ChannelHandler channelHandler);
    public abstract void channelNactive(ChannelHandler channelHandler);

    public abstract void channelRead(ChannelHandler channelHandler,Object msg);

    @Override
    public void completed(Integer result, Object attachment) {
        try {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            final long timeout = 60*60L;
            //读取数据
            channel.read(buffer, timeout, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    if (result ==-1) {
                        channelNactive(new ChannelHandler(channel, charset));
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            log.error("close channel happen exception");
                        }
                        return;
                    }
                        buffer.flip();
                        channelRead(new ChannelHandler(channel,charset),charset.decode(buffer));
                        buffer.clear();
                        channel.read(buffer,timeout, TimeUnit.SECONDS,null,this);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("happen exception in completed");
        }
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        exc.printStackTrace();
        log.error("failed");
    }
}
