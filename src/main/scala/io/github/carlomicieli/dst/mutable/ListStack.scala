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

import io.github.carlomicieli.dst.{FullStackException, EmptyStackException, Stack}
import io.github.carlomicieli.util.{Good, Bad, Or}

import scala.util.control.NoStackTrace

final class ListStack[A] private(st: LinkedList[A]) extends Stack[A] {

  private val storage: LinkedList[A] = st

  def push(item: A): Stack[A] Or FullStackException = {
    storage.addFront(item)
    Good(this)
  }

  def peek: Option[A] = st.headOption

  def size: Int = storage.size

  def isEmpty: Boolean = storage.isEmpty

  def pop(): (A, Stack[A]) Or EmptyStackException = {
    if (storage.isEmpty)
      Bad(new EmptyStackException)
    else {
      storage.removeHead.map {
        res =>
          val (k, _) = res
          (k, this)
      } orElse {
        new EmptyStackException with NoStackTrace
      }
    }
  }
}

object ListStack {
  def empty[A]: Stack[A] =
    new ListStack[A](LinkedList.empty[A])

  def apply[A](items: A*): Stack[A] = {
    val stack = ListStack.empty[A]
    if (items.isEmpty)
      stack
    else {
      for (i <- items.reverse) {
        stack.push(i)
      }
      stack
    }
  }
}
