package com.loopnow.library.socket.utilities

import com.loopnow.library.socket.Binding
import com.loopnow.library.socket.Channel

fun com.loopnow.library.socket.Channel.getBindings(event: String): List<com.loopnow.library.socket.Binding> {
  return bindings.toList().filter { it.event == event }
}