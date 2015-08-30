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
import io.github.carlomicieli.util.{Bad, Good, Or}

import scala.reflect.ClassTag
import scala.util.control.NoStackTrace

final class FixedCapacityQueue[A](st: Array[A]) extends Queue[A] {

  private val storage = st
  private val N: Int = storage.length
  private var first = 0
  private var last = 0
  private var empty = true

  def peek: Option[A] =
    if (isEmpty) None
    else Some(storage(first))

  def dequeue: (A, Queue[A]) Or EmptyQueueException = {
    if (isEmpty)
      Bad(new EmptyQueueException with NoStackTrace)
    else {
      val firstEl = storage(first)
      first = (first + 1) % N

      if (first == last) {
        empty = true
      }

      Good((firstEl, this))
    }
  }

  def size: Int =
    if (isEmpty)
      0
    else if (first < last)
      last - first
    else
      (last + N) - first

  def enqueue(el: A): Queue[A] Or FullQueueException = {
    if (isFull)
      Bad(new FullQueueException with NoStackTrace)
    else {
      if (empty) {
        empty = false
      }

      storage(last) = el
      last = (last + 1) % N
      Good(this)
    }
  }

  def isEmpty: Boolean = empty

  private def isFull = size == N
}

object FixedCapacityQueue {
  def apply[A: ClassTag](n: Int): Queue[A] = {
    val st = new Array[A](n)
    new FixedCapacityQueue[A](st)
  }
}