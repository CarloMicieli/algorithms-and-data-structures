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

import io.github.carlomicieli.util.{Good, Bad, Maybe, Or}

import scala.util.control.NoStackTrace

private[this]
class ListStack[+A](st: List[A]) extends Stack[A] {
  override def push[B >: A](el: B): Stack[B] = new ListStack[B](el +: st)

  override def size: Int = st.length

  override def top: Maybe[A] = st.headOption

  override def isEmpty: Boolean = st.isEmpty

  override def nonEmpty: Boolean = st.nonEmpty

  override def pop: Or[(A, Stack[A]), EmptyStackException] =
    if (isEmpty) Bad(new EmptyStackException with NoStackTrace)
    else {
      val head +: tail = st
      Good((head, new ListStack(tail)))
    }
}

private[this]
object ListStack {
  def empty[A]: Stack[A] = new ListStack(List.empty[A])
}