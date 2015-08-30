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
package io.github.carlomicieli.dst.immutable

import io.github.carlomicieli.util._

import scala.util.control.NoStackTrace

/**
 * It represents a LIFO data structure, the last element
 * added to the stack will be the first one to be removed.
 * @tparam A the `Queue` element type
 */
trait Stack[+A] {
  /**
   *
   * @param el
   * @tparam B
   * @return
   */
  def push[B >: A](el: B): Stack[B]

  /**
   *
   * @return
   */
  def pop: (A, Stack[A]) Or EmptyStackException

  /**
   * Check whether this `Stack` is empty
   * @return
   */
  def isEmpty: Boolean

  /**
   * Check whether this `Stack` is empty
   * @return
   */
  def nonEmpty: Boolean

  /**
   * Return the current size for this `Stack`
   * @return
   */
  def size: Int

  /**
   * Return the top element for the `Stack` (if exits), without removing it.
   * @return
   */
  def top: Maybe[A]
}

object Stack {
  def empty[A]: Stack[A] = new ListStack[A](List.empty[A])

  private class ListStack[A](st: List[A]) extends Stack[A] {
    def push[B >: A](el: B): Stack[B] =
      new ListStack[B](el +: st)

    def size: Int = st.length

    def top: Maybe[A] = if (nonEmpty) Just(st.head) else None

    def isEmpty: Boolean = st.isEmpty

    def nonEmpty: Boolean = st.nonEmpty

    def pop: Or[(A, Stack[A]), EmptyStackException] =
      if (isEmpty) Bad(new EmptyStackException with NoStackTrace)
      else {
        val head +: tail = st
        Good((head, new ListStack(tail)))
      }
  }
}

class EmptyStackException extends Exception("Stack is empty")

