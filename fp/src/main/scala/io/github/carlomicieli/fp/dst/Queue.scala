/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
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

/** It represents a FIFO data structure, the first element
  * added to the queue will be the first one to be removed.
  *
  * @tparam A the `Queue` element type
  */
trait Queue[+A] {
  /** Insert the new element to the last position of the `Queue`.
    *
    * @usecase def enqueue(el: A): Queue[A]
    * @inheritdoc
    * @param el the element to insert
    * @tparam A1 the element type
    * @return a new `Queue` with the element `el` inserted
    */
  def enqueue[A1 >: A](el: A1): Queue[A1]

  /** Remove the element from the front position (if any).
    * @return
    */
  def dequeue: (A, Queue[A]) Or EmptyQueueException

  /** Return the element in the front position, if exists.
    * @return optionally the front element
    */
  def peek: Maybe[A]

  /** Check whether this `Queue` is empty.
    * @return `true` if this `Queue` is empty; `false` otherwise
    */
  def isEmpty: Boolean

  /** Check whether this `Queue` is not empty.
    * @return `true` if this `Queue` is not empty; `false` otherwise
    */
  def nonEmpty: Boolean = !isEmpty

  /** Return the current size of the `Queue`.
    * @return the number of elements in this `Queue`
    */
  def size: Int
}

object Queue {
  def empty[A]: Queue[A] = new ListQueue()
}

class EmptyQueueException extends Exception