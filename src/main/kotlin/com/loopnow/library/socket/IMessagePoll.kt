package com.loopnow.library.socket

interface IMessagePool {
    fun acquireMessage():Message
    fun releaseMessage( message: Message)
}