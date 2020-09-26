package com.playboy.nio.lab1.client;

import com.playboy.nio.lab1.IoConstant;
import com.playboy.nio.lab1.handler.AioClientHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Aio 客户端
 */
@Slf4j
public class AioClient {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
        Future<Void> future = channel.connect(new InetSocketAddress("192.168.0.1", 7397));
        log.error("aio client Starting");
        future.get();
        channel.read(ByteBuffer.allocate(1024), null,
                new AioClientHandler(channel, Charset.forName(IoConstant.GBK)));
        Thread.sleep(100000);
    }
}
