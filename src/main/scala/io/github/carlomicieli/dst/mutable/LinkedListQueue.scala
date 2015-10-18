/*
 *                       __                  __
 *      ______________ _/ /___ _      ____ _/ /___ _____
 *     / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *    (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 *   /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                         /____/
 *  Copyright (c) 2015 the original author or authors.
 *  See the LICENCE.txt file distributed with this work for additional
 *  information regarding copyright ownership.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.util.{Bad, Good, Maybe}

private[this]
class LinkedListQueue[A] extends Queue[A] {
  private val st = LinkedList.empty[A]

  override def peek: Maybe[A] = st.headOption

  override def dequeue(): A = {
    st.removeHead() match {
      case Good(v) => v
      case Bad(_)  => throw new EmptyQueueException
    }
  }

  override def size: Int = st.length

  override def enqueue(x: A): Unit = st.addBack(x)

  override def isEmpty: Boolean = st.isEmpty
}

/**
 * A Queue based on linked list.
 */
object LinkedListQueue {
  /**
   * Creates a new empty queue.
   * @tparam A the element type
   * @return an empty queue
   */
  def empty[A]: Queue[A] = new LinkedListQueue[A]
}