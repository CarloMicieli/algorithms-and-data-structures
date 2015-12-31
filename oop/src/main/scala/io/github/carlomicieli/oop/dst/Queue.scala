/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
 *
 * Copyright (c) 2015 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.carlomicieli.oop.dst

import scala.reflect.ClassTag

/**
  * It represents a mutable ''FIFO (first-in, first-out)'' data structure.
  *
  * @tparam A the element type
  */
trait Queue[A] {
  /**
    * Returns the first element in this queue, without removing it.
    * @return optionally the top element
    */
  def peek: Option[A]

  /**
    * Inserts the element at the end of the list.
    * @param x the new element to add
    */
  def enqueue(x: A): Unit

  /**
    * Returns the first element and remove it from this queue.
    * @return the first element
    */
  def dequeue(): A

  /**
    * Returns the number of elements in this queue.
    * @return the number of elements
    */
  def size: Int

  /**
    * Checks whether this queue is empty, or not.
    * @return `true` if this queue is empty, `false` otherwise
    */
  def isEmpty: Boolean
}

object Queue {
  def empty[A]: Queue[A] = new ListQueue[A]
  def fixed[A: ClassTag](n: Int): Queue[A] = {
    val storage = new Array[A](n)
    new FixedCapacityQueue[A](storage)
  }
}

class EmptyQueueException extends Exception("Queue is empty")
class FullQueueException extends Exception("Queue is full")