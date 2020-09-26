package com.playboy.nio.lab1.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
@Slf4j
public class AioServer extends Thread {
    private AsynchronousServerSocketChannel serverSocketChannel;

    @Override
    public void run() {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open(AsynchronousChannelGroup
                    .withCachedThreadPool(Executors.newCachedThreadPool(), 10));
            serverSocketChannel.bind(new InetSocketAddress(7397));
            log.error("=========Aio server starting ==========");
            //等待
            CountDownLatch latch = new CountDownLatch(1);
            serverSocketChannel.accept(this,new AioServerChannelInitializer());
            latch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            log.error("aio server happen exception ! message is :{}",e.getMessage());
        }

    }

    public AsynchronousServerSocketChannel serverSocketChannel(){
        return serverSocketChannel;
    }

    public static void main(String[] args) {
        new AioServer().start();
    }
}
