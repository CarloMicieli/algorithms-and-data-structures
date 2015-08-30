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
package io.github.carlomicieli.searching

import io.github.carlomicieli.dst.mutable.LinkedList

final class SequentialSearchST[K, V] extends SymbolTable[K, V] {
  private type KeyValPair = (K, V)
  private val storage: LinkedList[KeyValPair] = LinkedList.empty[KeyValPair]

  def update(key: K, value: V): Unit = {
    containsKey(key) foreach {
      n => storage.remove(n)
    }
    storage.addFront((key, value))
  }

  def get(key: K): Option[V] =
    containsKey(key).map(p => p._2)

  def size: Int = storage.length

  def delete(key: K): Unit = {
    containsKey(key) foreach {
      n => storage.remove(n)
    }
  }

  def keys: Iterable[K] = storage.elements.map(p => p._1)

  def apply(key: K): V = get(key).get

  def contains(key: K): Boolean =
    containsKey(key).isDefined

  def isEmpty: Boolean = storage.isEmpty

  private def containsKey(key: K): Option[KeyValPair] = {
    storage.find(p => p._1 == key)
  }
}

object SequentialSearchST {
  def empty[K, V]: SymbolTable[K, V] = new SequentialSearchST[K, V]()
}