package com.playboy.nio.lab1;

import com.playboy.nio.lab1.server.AioServer;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * channel 初始化器
 *
 * CompletionHandler
 *  V - I/O 操作的结果类型
 *  A - 附加到 I/O 操作的对象的类型
 *
 */
@Slf4j
public abstract class ChannelInitializer implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

    /**
     * IO 操作成功完成时，将调用 completed 方法
     * @param result
     * @param attachment
     */
    @Override
    public void completed(AsynchronousSocketChannel result, AioServer attachment) {
        try {
            initChannel(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("initChannel happen exception ! message is :{}",e.getMessage());
        }finally {
            //接收客户端连接
            attachment.serverSocketChannel().accept(attachment,this);
        }
    }

    /**
     * I/O 操作失败调用方法
     * @param exc
     * @param attachment
     */
    @Override
    public void failed(Throwable exc, AioServer attachment) {
        exc.getStackTrace();
    }

    /**
     * 初始化 channel
     * @param channel
     * @throws Exception
     */
    protected abstract void initChannel(AsynchronousSocketChannel channel) throws Exception;
}
