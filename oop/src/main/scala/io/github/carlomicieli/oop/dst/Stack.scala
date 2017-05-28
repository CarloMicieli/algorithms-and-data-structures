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

package io.github.carlomicieli.oop.dst

/**
  * It represents a mutable `Stack`, a ''LIFO (last in, first out)'' data structure.
  *
  * This implementation provides constant time operations for `push`, `pop` and `top`. The implementor classes
  * shouldn't provide any operation to look inside the data structure. Elements must added/removed only from the top
  * position in the stack.
  *
  * @tparam A the element type
  */
trait Stack[A] {
  /**
    * Adds a new element `x` into the top position in the `Stack`.
    * @param x the new element
    */
  def push(x: A): Unit

  /**
    * Removes the element in the top position in the `Stack`
    * @return
    */
  def pop(): A

  /**
    * Optionally returns the top element from this `Stack`.
    * @return optionally the top element
    */
  def top: Option[A]

  /**
    * Checks whether this `Stack` is empty.
    * @return `true` if the `Stack` is empty; `false` otherwise
    */
  def isEmpty: Boolean

  /**
    * Checks whether this `Stack` is not empty.
    * @return `true` if the `Stack` is not empty; `false` otherwise
    */
  def nonEmpty: Boolean

  /**
    * Returns the number of elements contained in the `Stack`.
    * @return the number of elements
    */
  def size: Int
}

object Stack {
  def empty[A]: Stack[A] = new LinkedListStack[A]
}

class FullStackException extends Exception("Stack is full")
class EmptyStackException extends Exception("Stack is empty")