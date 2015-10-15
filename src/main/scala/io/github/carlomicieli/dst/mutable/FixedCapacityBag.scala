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

import scala.reflect.ClassTag

private[this]
class FixedCapacityBag[A] private(st: Array[A]) extends Bag[A] {

  private val storage = st
  private var ind = 0

  def add(el: A): Unit = {
    storage(ind) = el
    ind = ind + 1
  }

  def size: Int = ind
  def contains(el: A): Boolean = {
    @annotation.tailrec
    def loop(i: Int): Boolean =
      if (i == ind) false
      else {
        if (storage(i) == el) true
        else loop(i + 1)
      }

    loop(0)
  }

  def isEmpty: Boolean = ind == 0
}

object FixedCapacityBag {
  def apply[A: ClassTag](size: Int): Bag[A] = {
    val storage = new Array[A](size)
    new FixedCapacityBag[A](storage)
  }
}