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

import io.github.carlomicieli.dst.{EmptyStackException, FullStackException, Stack}
import io.github.carlomicieli.util.{Good, Bad, Or}

import scala.reflect.ClassTag
import scala.util.control.NoStackTrace

final class FixedCapacityStack[A] private(st: Array[A]) extends Stack[A] {
  private val storage = st
  private var top = 0

  private def isFull = storage.length == top

  def peek: Option[A] =
    if (isEmpty) None else Some(storage(top - 1))

  def push(el: A): Stack[A] Or FullStackException = {
    if (isFull)
      Bad(new FullStackException with NoStackTrace)
    else {
      storage(top) = el
      top = top + 1
      Good(this)
    }
  }

  def pop(): (A, Stack[A]) Or EmptyStackException = {
    if (isEmpty)
      Bad(new EmptyStackException with NoStackTrace)
    else {
      top = top - 1
      val e = storage(top)
      Good((e, this))
    }
  }

  def size = top

  def isEmpty = top == 0
}

object FixedCapacityStack {
  def apply[A: ClassTag](size: Int): Stack[A] = {
    val st: Array[A] = new Array[A](size)
    new FixedCapacityStack[A](st)
  }
}