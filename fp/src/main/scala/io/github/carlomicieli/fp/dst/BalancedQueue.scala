/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                   /____/
 * Copyright (c) 2017 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.carlomicieli.fp.dst

private[this]
class BalancedQueue[+A](front: SizedList[A], rear: SizedList[A]) extends Queue[A] {

  // O(1) amortized; [O(n) for some operation]
  override def enqueue[A1 >: A](x: A1): Queue[A1] =
    balance(front, x +: rear)

  // O(1)
  override def peek: Maybe[A] = front.headOption

  // O(1) amortized; [O(n) for some operation]
  override def dequeue: Or[(A, Queue[A]), EmptyQueueException] = {
    if (isEmpty)
      Bad(new EmptyQueueException)
    else {
      val head = front.head
      val tail = front.tail
      Good((head, BalancedQueue(tail, rear)))
    }
  }

  // O(1)
  override def size: Int = front.size + rear.size

  // O(1)
  override def isEmpty: Boolean = front.isEmpty

  private def balance[A1 >: A](f: SizedList[A1], r: SizedList[A1]): BalancedQueue[A1] = {
    if (r.size <= f.size)
      BalancedQueue(f, r)
    else
      BalancedQueue(f union r.reverse, SizedList.empty[A])
  }
}

object BalancedQueue {
  def empty[A]: Queue[A] = BalancedQueue(SizedList.empty[A], SizedList.empty[A])

  private def apply[A](f: SizedList[A], r: SizedList[A]): BalancedQueue[A] =
    new BalancedQueue(f, r)
}