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

package io.github.carlomicieli.oop.dst

import scala.reflect.ClassTag

private[this] class FixedCapacityStack[A](st: Array[A]) extends Stack[A] {
  private val storage = st
  private var topIndex = 0

  def push(el: A): Unit = {
    if (isFull)
      throw new FullStackException

    storage(topIndex) = el
    topIndex = topIndex + 1
  }

  def size: Int = topIndex

  def top: Option[A] =
    if (isEmpty) None else Some(storage(topIndex - 1))

  def isEmpty: Boolean = topIndex == 0

  def nonEmpty: Boolean = topIndex != 0

  def pop(): A = {
    if (isEmpty)
      throw new EmptyStackException

    topIndex = topIndex - 1
    storage(topIndex)
  }

  private def isFull = storage.length == topIndex
}

/** A Stack implementation based on an Array.
  */
object FixedCapacityStack {
  def empty[A: ClassTag]: Stack[A] = FixedCapacityStack.apply[A](16)

  def apply[A: ClassTag](size: Int): Stack[A] = {
    val st: Array[A] = new Array[A](size)
    new FixedCapacityStack[A](st)
  }
}
