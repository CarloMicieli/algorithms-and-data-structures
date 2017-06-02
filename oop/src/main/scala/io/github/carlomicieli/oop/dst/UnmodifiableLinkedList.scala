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

private[this] class UnmodifiableLinkedList[A](inner: LinkedList[A]) extends LinkedList[A] {
  require(inner != null)

  override def headOption: Option[A] = inner.headOption

  override def lastOption: Option[A] = inner.lastOption

  override def foldLeft[B](z: B)(f: (B, A) => B): B = inner.foldLeft(z)(f)

  override def foldRight[B](z: B)(f: (A, B) => B): B = inner.foldRight(z)(f)

  override def length: Int = inner.length

  override def elements: Iterable[A] = inner.elements

  override def contains(key: A): Boolean = inner.contains(key)

  override def mkString(sep: String, start: String, end: String): String = {
    inner.mkString(sep, start, end)
  }

  override def nonEmpty: Boolean = inner.nonEmpty

  override def isEmpty: Boolean = inner.isEmpty

  override def find(p: (A) => Boolean): Option[A] = inner.find(p)

  override def foreach[U](f: (A) => U): Unit = inner.foreach(f)

  override def update[B, C](key: A)(implicit ev: (A) => (B, C)): Boolean = invalidOp

  override def insert(key: A)(implicit ord: Ordering[A]): Unit = invalidOp

  override def prepend(el: A): Unit = invalidOp

  override def clear(): Unit = invalidOp

  override def removeHead(orElse: => A): A = invalidOp

  override def append(el: A): Unit = invalidOp

  override def remove(key: A): Boolean = invalidOp

  private def invalidOp = throw new UnsupportedOperationException("This list is unmodifiable")
}