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

package io.github.carlomicieli.oop.searching

import io.github.carlomicieli.oop.dst.{ KeyValuePair, DynamicArray }

import scala.reflect.ClassTag

class BinarySearchST[K: Ordering, V] private (st: DynamicArray[KeyValuePair[K, V]]) extends SymbolTable[K, V] {

  private var s = 0

  def update(key: K, value: V): Unit = ???

  def get(key: K): Option[V] = ???

  def size: Int = s

  def delete(key: K): Unit = ???

  def keys: Iterable[K] = ???

  def isDefinedAt(key: K): Boolean = ???

  def apply(key: K): V = ???

  def contains(key: K): Boolean = ???

  def isEmpty: Boolean = s == 0

  override def toString = st.toString

  private def isFull = false

  private def binarySearch(k: K): Option[Int] = ???
}

object BinarySearchST {
  def apply[K: ClassTag, V: ClassTag](size: Int)(implicit ord: Ordering[K]): SymbolTable[K, V] = {
    val st = DynamicArray.empty[KeyValuePair[K, V]](size)
    new BinarySearchST[K, V](st)
  }
}