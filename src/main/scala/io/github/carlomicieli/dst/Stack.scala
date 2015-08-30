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

import io.github.carlomicieli.util.{Bad, Good, Or}

trait Stack[A] {
  def push(item: A): Stack[A] Or FullStackException
  def peek: Option[A]
  def pop(): (A, Stack[A]) Or EmptyStackException
  def isEmpty: Boolean
  def nonEmpty: Boolean = !isEmpty

  def size: Int

  override def toString = {
    val elems = peek.map { t => s"top = $t" } getOrElse { "" }
    s"Stack($elems)"
  }
}

case class InvalidStackOperation[A](stack: Stack[A], op: StackOp[A], ex: Exception)
class EmptyStackException extends Exception("Stack is empty")
class FullStackException extends Exception("Stack is full")

sealed trait StackOp[+A]
case object PopOp extends StackOp[Nothing]
case class PushOp[A](el: A) extends StackOp[A]

object StackOp {
  def sequence[A](initial: Stack[A], ops: Seq[StackOp[A]]): Stack[A] Or InvalidStackOperation[A] = {
    if (ops.isEmpty)
      Good(initial)
    else {
      val head +: tail = ops
      head match {
        case PushOp(k) =>
          initial.push(k) match {
            case Good(newStack) =>
              sequence(newStack, tail)
            case Bad(err) =>
              Bad(InvalidStackOperation(initial, head, err))
          }

        case PopOp =>
          initial.pop match {
            case Good((_, newStack)) =>
              sequence(newStack, tail)
            case Bad(err) =>
              Bad(InvalidStackOperation(initial, head, err))
          }
      }
    }
  }
}