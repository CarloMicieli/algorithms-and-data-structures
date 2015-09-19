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

import io.github.carlomicieli.util.Maybe

private[this]
case class SizedList[+A](xs: List[A], size: Int) {
  def this() = this(List.empty[A], 0)

  def isEmpty = size == 0

  def head: A = xs.head
  def headOption: Maybe[A] = xs.headOption

  def tail: SizedList[A] = {
    if (isEmpty)
      this
    else
      SizedList(xs.tail, size - 1)
  }

  def +:[A1 >: A](x: A1) = SizedList(x +: xs, size + 1)

  def union[A1 >: A](that: SizedList[A1]): SizedList[A1] = {
    (this, that) match {
      case (SizedList(Nil, _), sl2) => sl2
      case (sl1, SizedList(Nil, _)) => sl1
      case (SizedList(ys, len1), SizedList(zs, len2)) =>
        SizedList(ys ++ zs, len1 + len2)
    }
  }

  def reverse: SizedList[A] = SizedList(xs.reverse, size)
}

private[this]
object SizedList {
  def empty[A]: SizedList[A] = new SizedList[A]
}
