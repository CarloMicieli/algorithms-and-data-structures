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
sealed trait BinarySearchTree[+K, +V] extends Tree[K, V] {
  def get: (K, V)
  def lookup[K1 >: K](key: K1)(implicit ord: Ordering[K1]): Maybe[(K1, V)] = ???
  def insert[K1 >: K, V1 >: V](key: K1, value: V1)(implicit ord: Ordering[K1]): Tree[K1, V1] = ???
  def max: Maybe[K] = ???
  def size: Int = ???
  def toList: List[(K, V)] = ???
  def delete[K1 >: K](key: K1)(implicit ord: Ordering[K1]): (Maybe[V], Tree[K1, V]) = ???
  def min: Maybe[K] = ???
  def isEmpty: Boolean
  def depth: Int = ???
  def upsert[K1 >: K, V1 >: V](key: K1, value: V1)(f: (V1) => V1)(implicit ord: Ordering[K1]): Tree[K1, V1] = ???
}

private[this]
case object EmptyTree extends BinarySearchTree[Nothing, Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("Tree.get: this tree is empty")
}

private[this]
case class Node[K, V](key: K, value: V) extends BinarySearchTree[K, V] {
  def isEmpty = false
  def get: (K, V) = (key, value)
}