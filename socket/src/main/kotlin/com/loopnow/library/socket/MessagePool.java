package com.loopnow.library.socket;

import org.jetbrains.annotations.NotNull;

public class MessagePool implements IMessagePool {
    private Pools.SynchronizedPool<Message> pool;
    private static MessagePool messagePool;

    private MessagePool() {
        pool = new Pools.SynchronizedPool<Message>(500);
    }

    public static synchronized MessagePool getInstance() {
        if (messagePool == null) {
            messagePool = new MessagePool();
        }
        return messagePool;
    }

    public Pools.SynchronizedPool<Message> getPool() {
        return pool;
    }


    @NotNull
    @Override
    public Message acquireMessage() {
        try {
            Message message = getInstance().getPool().acquire();
            return message == null ? new Message() : message;
        } catch (Exception e) {
            return new Message();
        }
    }

    @Override
    public void releaseMessage(@NotNull Message message) {
        try {
            getInstance().getPool().release(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
