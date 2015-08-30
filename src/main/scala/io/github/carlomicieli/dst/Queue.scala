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
package io.github.carlomicieli.dst

import io.github.carlomicieli.util.Or

trait Queue[A] {
  def peek: Option[A]
  def enqueue(el: A): Queue[A] Or FullQueueException
  def dequeue: (A, Queue[A]) Or EmptyQueueException
  def size: Int
  def isEmpty: Boolean
}

class InvalidQueueOperation[A](queue: Queue[A], el: A, err: Exception)
class EmptyQueueException extends Exception("Queue is empty")
class FullQueueException extends Exception("Queue is full")

sealed trait QueueOp[+A]
case class Enquque[A](el: A) extends QueueOp[A]
case object DequeueOp extends QueueOp[Nothing]

object QueueOp {
  def sequence[A](initial: Queue[A], ops: Seq[QueueOp[A]]): Queue[A] Or InvalidQueueOperation[A] = ???
}

