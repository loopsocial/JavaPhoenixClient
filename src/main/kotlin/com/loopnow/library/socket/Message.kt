/*
 * Copyright (c) 2019 Daniel Rees <daniel.rees18@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.loopnow.library.socket

import com.google.gson.annotations.SerializedName


data class Message(
    /** The unique string ref. Empty if not present */
    @SerializedName("ref")
    var ref: String = "",

    /** The message topic */
    @SerializedName("topic")
    var topic: String = "",

    /** The message event name, for example "phx_join" or any other custom name */
    @SerializedName("event")
    var event: String = "",

    /** The payload of the message */
    @SerializedName("payload")
    var payload: Payload = HashMap(),

    /** The ref sent during a join event. Empty if not present. */
    @SerializedName("join_ref")
    var joinRef: String? = null
) {

    fun duplicate(other: Message) {
       this.copy(other.ref, other.topic,other. event, other.payload, other.joinRef)
    }


    /**
     * Convenience var to access the message's payload's status. Equivalent
     * to checking message.payload["status"] yourself
     */
    var status: String? = null
        get() = payload["status"] as? String

    companion object {
        private var pool: IMessagePool? = null

        fun initPool(){
            if (pool==null){
                pool = MessagePool.getInstance()
            }
        }
        fun acquireMessage(): Message {
            pool?.let {
                return it.acquireMessage()
            } ?: let {
                return Message()
            }
        }

        fun releaseMessage(message: Message){
            pool?.let {
                return it.releaseMessage(message)
            }
        }
    }
}
