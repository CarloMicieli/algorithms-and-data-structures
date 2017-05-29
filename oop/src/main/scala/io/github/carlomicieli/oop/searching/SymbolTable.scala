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

package io.github.carlomicieli.oop.searching

/** A ''symbol table'' is a data structure for key-value pairs that supports two
  * operations:
  *
  * - `insert` (put) a new pair into the table
  * - `search` (get) for the value associated with a given key.
  *
  * @tparam K the key type
  * @tparam V the value type
  */
trait SymbolTable[K, V] extends PartialFunction[K, V] {

  def apply(key: K): V

  def isDefinedAt(key: K): Boolean

  /** Put the entry key-value in this symbol table. If the given `key` is already
    * present then the corresponding value will be replaced with the new value.
    *
    * @param key the key
    * @param value the corresponding value
    */
  def update(key: K, value: V): Unit

  /** Optionally returns the value associated with the given `key`.
    * @param key the key to search
    * @return optionally the value if found; a `None` otherwise
    */
  def get(key: K): Option[V]

  /** Removes the entry with the given `key` from the symbol table.
    * @param key the key to remove
    */
  def delete(key: K): Unit

  /** Checks whether this symbol table contains the given `key`.
    * @param key the key to search
    * @return `true` if the symbol table contains the `key`; `false` otherwise
    */
  def contains(key: K): Boolean

  /** Checks whether this symbol table is empty.
    * @return `true` if empty; `false` otherwise.
    */
  def isEmpty: Boolean

  /** Returns the number of entry contained in this symbol table.
    * @return the number of entry
    */
  def size: Int

  def keys: Iterable[K]
}