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

/**
 * It represents a "lazy" list where `head` and `tail` are not evaluated eagerly.
 * @tparam A the element type
 */
sealed trait LazyList[+A] {
  def head: A
  def tail: LazyList[A]
  def isEmpty: Boolean
  def size: Int
  def ++[B >: A](that: LazyList[B]): LazyList[B]

  def map[B](f: A => B): LazyList[B]
  def flatMap[B](f: A => LazyList[B]): LazyList[B]

  def take(n: Int): LazyList[A]
  def drop(n: Int): LazyList[A]

  def filter(p: A => Boolean): LazyList[A]
  def nonFilter(p: A => Boolean): LazyList[A]

  def foldRight[B](z: B)(f: (A, B) => B): B

  def foldLeft[B](z: B)(f: (B, A) => B): B

  def toList: List[A]
}

object LazyList {
  def apply[A](items: A*): LazyList[A] = ???
}
