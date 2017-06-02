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

import scala.util.Try

private[this] class DoublyLinkedList[A] extends LinkedList[A] {

  private sealed trait Cell {
    var prev: Cell
    var next: Cell
    def value: A
    def isEmpty: Boolean

    def valueOption: Option[A] = {
      if (isEmpty)
        None
      else
        Some(value)
    }
  }

  private class ValueCell(val value: A) extends Cell {
    override var prev: Cell = _
    override var next: Cell = _
    override def isEmpty: Boolean = false
  }

  private class Nil extends Cell {
    override var prev: Cell = _
    override var next: Cell = _
    override def isEmpty: Boolean = true
    override def value: A = throw new NoSuchElementException("Nil cell: no value found")
  }

  private var h: Cell = new Nil
  private var l: Cell = new Nil

  override def headOption: Option[A] = h.valueOption

  override def lastOption: Option[A] = l.valueOption

  override def removeHead(): Try[A] = ???

  override def isEmpty: Boolean = h.isEmpty

  override def nonEmpty: Boolean = ???

  override def prepend(el: A): Unit = ???

  override def append(x: A): Unit = ???

  override def insert(key: A)(implicit ord: Ordering[A]): Unit = ???

  override def foreach[U](f: (A) => U): Unit = ???

  override def length: Int = ???

  override def foldLeft[B](z: B)(f: (B, A) => B): B = ???

  override def foldRight[B](z: B)(f: (A, B) => B): B = ???

  override def mkString(sep: String, start: String, end: String): String = ???

  override def elements: Iterable[A] = ???

  override def contains(key: A): Boolean = ???

  override def find(p: (A) => Boolean): Option[A] = ???

  override def remove(key: A): Boolean = ???

  override def update[B, C](key: A)(implicit ev: (A) => (B, C)): Boolean = ???

  override def clear(): Unit = ???
}

object DoublyLinkedList {
  def empty[A]: LinkedList[A] = new DoublyLinkedList[A]
}