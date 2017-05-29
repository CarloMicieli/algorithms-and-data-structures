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

package io.github.carlomicieli.oop.dst

/** @tparam A the item type
  */
trait Bag[A] {
  /** Add an item to this bag
    * @param x the element to add
    */
  def add(x: A): Unit

  /** Checks whether this bag is empty
    * @return `true` if it's empty; `false` otherwise
    */
  def isEmpty: Boolean

  /** Returns the number of items contained in this bag.
    * @return the number of items
    */
  def size: Int

  /** Checks whether this bag contains the given item
    * @param x the item to search
    * @return `true` if the bag contains the item; `false` otherwise
    */
  def contains(x: A): Boolean
}