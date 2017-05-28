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
case class ListQueue[+A](front: List[A], rear: List[A]) extends Queue[A] {

  def this() = this(List.empty[A], List.empty[A])

  override def enqueue[A1 >: A](el: A1): Queue[A1] = ListQueue(front, el +: rear)

  override def peek: Maybe[A] =
    if (isEmpty)
      None
    else {
      front match {
        case x +: _ => Just(x)
        case _      => rear.reverse.headOption
      }
    }

  override def dequeue: Or[(A, Queue[A]), EmptyQueueException] =
    if (isEmpty)
      Bad(new EmptyQueueException)
    else {
      front match {
        case x +: xs => Good((x, ListQueue(xs, rear)))
        case _ =>
          val newFront = rear.reverse
          Good((newFront.head, ListQueue(newFront.tail, List.empty[A])))
      }
    }

  override def size: Int = front.length + rear.length

  override def isEmpty: Boolean = front.isEmpty && rear.isEmpty

  override def toString: String = {
    val topEl = peek.map(x => s"top = $x").getOrElse("")
    s"Queue($topEl)"
  }
}