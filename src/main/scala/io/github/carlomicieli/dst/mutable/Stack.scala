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

import io.github.carlomicieli.util.Maybe

import scala.reflect.ClassTag

trait Stack[A] {
  def push(el: A): Unit
  def pop(): A
  def top: Maybe[A]
  def isEmpty: Boolean
  def nonEmpty: Boolean
  def size: Int
}

object Stack {
  def empty[A]: Stack[A] = new ListStack[A]

  def fixed[A: ClassTag](size: Int): Stack[A] = {
    val st: Array[A] = new Array[A](size)
    new FixedCapacityStack[A](st)
  }
}

class FullStackException extends Exception("Stack is full")
class EmptyStackException extends Exception("Stack is empty")