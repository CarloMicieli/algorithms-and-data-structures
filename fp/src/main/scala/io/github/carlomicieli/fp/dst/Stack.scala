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

/** It represents a LIFO data structure, the last element
  * added to the stack will be the first one to be removed.
  *
  * This data structure is immutable; if an operation is changing the stack, it will
  * return a new, modified `Stack`.
  *
  * @tparam A the element type
  */
trait Stack[+A] {
  /** `O(1)` Creates a new `Stack` with the provided value `el` as its top element.
    *
    * @usecase def push(el: A): Stack[A]
    * @inheritdoc
    * @param el the top element
    * @tparam B the element type
    * @return a new `Stack` with `el` as its top element
    */
  def push[B >: A](el: B): Stack[B]

  /** `O(1)` If this `Stack` is not empty, it returns a pair with the top element and a new `Stack` without this element;
    * else it returns an `EmptyStackException` wrapped in a `Bad` value.
    * @return if not empty, a pair with the top element and a new `Stack`;
    * an `EmptyStackException` wrapped in a `Bad` value otherwise
    */
  def pop: (A, Stack[A]) Or EmptyStackException

  /** `O(k)` Eventually pops the top k elements in this Stack until they match the given predicate `p`.
    * @param p the predicate to match
    * @return a pair with the list of elements popped and the new Stack
    */
  def popUntil(p: A => Boolean): (List[A], Stack[A])

  /** `O(1)` Check whether this `Stack` is empty.
    * @return `true` if this `Stack` is empty; `false` otherwise
    */
  def isEmpty: Boolean

  /** `O(1)` Check whether this `Stack` is not empty.
    * @return `true` if this `Stack` is not empty; `false` otherwise
    */
  def nonEmpty: Boolean

  /** `O(n)` Return the current size for this `Stack`.
    * @return the number of elements
    */
  def size: Int

  /** `O(1)` Return the top element for the `Stack` (if exits), without removing it.
    * @return optionally the top element
    */
  def top: Maybe[A]

  /** `O(n)` Apply the function `f` to all stack elements, just for its side-effects.
    * @param f the function to apply
    * @tparam U
    */
  def foreach[U](f: A => U): Unit
}

object Stack {
  /** Creates a new empty `Stack`.
    * @tparam A the element type
    * @return a new empty `Stack`
    */
  def empty[A]: Stack[A] = new ListStack[A](List.empty[A])

  /** Creates a new `Stack`, applying the list of operations `ops` in order.
    * If any of the operations produce a wrong state, it is silently ignored.
    *
    * @param ops the list of operation
    * @tparam T the `Stack` element type
    * @return a new `Stack`
    */
  def fromOps[T](ops: List[StackOp[T]]): Stack[T] = {
    def step(stack: Stack[T], op: StackOp[T]): Stack[T] = op match {
      case PushOp(v) => stack push v
      case PopOp =>
        if (stack.isEmpty)
          stack
        else {
          val (_, st) = stack.pop.get
          st
        }
    }

    ops.foldLeft(ListStack.empty[T])(step)
  }
}

class EmptyStackException extends Exception("Stack is empty")