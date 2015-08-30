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

import io.github.carlomicieli.dst.{FullQueueException, EmptyQueueException, Queue}
import io.github.carlomicieli.util.{Good, Or}

import scala.util.control.NoStackTrace

final class ListQueue[A] private(st: LinkedList[A]) extends Queue[A] {
  private val storage = LinkedList.empty[A]

  def enqueue(el: A): Queue[A] Or FullQueueException = {
    storage.addBack(el)
    Good(this)
  }

  def dequeue: (A, Queue[A]) Or EmptyQueueException = {
    storage.removeHead.map {
      x => (x._1, this)
    } orElse {
      new EmptyQueueException with NoStackTrace
    }
  }

  def peek: Option[A] = storage.headOption

  def size: Int = storage.size

  def isEmpty: Boolean = storage.isEmpty
}

object ListQueue {
  def empty[A]: Queue[A] =
    new ListQueue[A](LinkedList.empty[A])
}