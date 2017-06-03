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

private[this] class DoublyLinkedList[A] extends LinkedList[A] {

  private sealed trait Node {
    var prev: Node
    var next: Node

    var key: A

    def isNil: Boolean

    def matches(p: A => Boolean): Boolean = keyOption.exists(p)

    def keyOption: Option[A] = {
      if (isNil)
        None
      else
        Some(key)
    }

    def toOption: Option[Node] = {
      if (isNil)
        None
      else
        Some(this)
    }

    override def toString: String = {
      keyOption.map(_.toString).getOrElse("<nil>")
    }
  }

  private class ValueNode(var key: A) extends Node {
    override var prev: Node = Nil
    override var next: Node = Nil

    override def isNil: Boolean = false
  }

  private object Nil extends Node {
    def next = throw new NoSuchElementException("Nil node has no next")

    def next_=(n: Node): Unit = {}

    def prev = throw new NoSuchElementException("Nil node has no next")

    def prev_=(n: Node): Unit = {}

    override def isNil: Boolean = true

    override def key: A = throw new NoSuchElementException("Nil cell: no value found")
    override def key_=(x: A): Unit = {}
  }

  private class Sentinel extends Node {
    override var prev: Node = Nil
    override var next: Node = Nil

    override def isNil: Boolean = true

    override def key: A = throw new NoSuchElementException("Sentinel cell: no value found")
    override def key_=(x: A): Unit = {}
  }

  private val firstNode: Node = new Sentinel
  private val lastNode: Node = new Sentinel
  firstNode.next = lastNode
  lastNode.prev = firstNode

  override def headOption: Option[A] = firstNode.next.keyOption

  override def lastOption: Option[A] = lastNode.prev.keyOption

  override def isEmpty: Boolean = firstNode.next.isNil && lastNode.prev.isNil

  override def prepend(x: A): Unit = {
    val newCell = new ValueNode(x)
    if (isEmpty) {
      initList(newCell)
    } else {
      val oldNext = firstNode.next
      firstNode.next = newCell

      newCell.prev = firstNode
      newCell.next = oldNext
      oldNext.prev = newCell
    }
  }

  override def append(x: A): Unit = {
    val newCell = new ValueNode(x)
    if (isEmpty) {
      initList(newCell)
    } else {
      val oldPrev = lastNode.prev
      lastNode.prev = newCell

      newCell.prev = oldPrev
      newCell.next = lastNode
      oldPrev.next = newCell
    }
  }

  override def foldLeft[B](z: B)(f: (B, A) => B): B = {
    if (isEmpty) {
      z
    } else {
      loop(firstNode.next)(z)((acc, node) => f(acc, node.key))
    }
  }

  override def foldRight[B](z: B)(f: (A, B) => B): B = {
    if (isEmpty) {
      z
    } else {
      var acc: B = z
      var node = lastNode.prev
      while (!node.isNil) {
        acc = f(node.key, acc)
        node = node.prev
      }
      acc
    }
  }

  override def insert(key: A)(implicit ord: Ordering[A]): Unit = ???

  override def mkString(sep: String, start: String, end: String): String = {
    val elements =
      if (isEmpty) {
        ""
      } else {
        val initNode: Node = firstNode.next
        loop(initNode.next)(initNode.toString)((str, x) => s"$str$sep$x")
      }

    s"$start$elements$end"
  }

  override def elements: Iterable[A] = new Iterable[A] {
    override def iterator: Iterator[A] = new Iterator[A] {
      var curr: Node = DoublyLinkedList.this.firstNode.next
      override def hasNext: Boolean = !curr.isNil
      override def next(): A = {
        val k = curr.key
        curr = curr.next
        k
      }
    }
  }

  override def find(p: (A) => Boolean): Option[A] = {
    findNode(p).map(_.key)
  }

  override def remove(key: A): Boolean = {
    findNode(_ == key) match {
      case None =>
        false
      case Some(n) =>
        firstNode.next = n.next
        lastNode.prev = n.prev
        true
    }
  }

  override def update[B, C](newKey: A)(implicit ev: (A) => (B, C)): Boolean = {
    findNode(_._1 == newKey._1) match {
      case None =>
        append(newKey)
        true
      case Some(curr) =>
        curr.key = newKey
        false
    }
  }

  override def equals(o: scala.Any): Boolean = {
    o match {
      case that: DoublyLinkedList[A] => DoublyLinkedList.areEquals(this, that)
      case _                         => false
    }
  }

  override def clear(): Unit = {
    firstNode.next = lastNode
    lastNode.prev = firstNode
  }

  private def loop[B](startingNode: Node)(z: B)(f: (B, Node) => B): B = {
    var acc: B = z
    var node = startingNode
    while (!node.isNil) {
      acc = f(acc, node)
      node = node.next
    }
    acc
  }

  private def findNode(p: (A) => Boolean): Option[Node] = {
    if (isEmpty) {
      None
    } else {
      var node = firstNode.next
      while (!node.isNil && !node.matches(p)) {
        node = node.next
      }

      node.toOption
    }
  }

  private def initList(newCell: Node): Unit = {
    firstNode.next = newCell
    lastNode.prev = newCell

    newCell.prev = firstNode
    newCell.next = lastNode
  }
}

/** Define a linked list in which each node keeps an explicit reference to the node before it and a
  * reference to the node after it.
  *
  * @author Carlo Micieli
  * @since 1.0
  */
object DoublyLinkedList {
  /** Creates an empty doubly linked list.
    * @tparam A the list element type
    * @return an empty list
    */
  def empty[A]: LinkedList[A] = new DoublyLinkedList[A]

  /** Creates a doubly linked list with the given items.
    * @param items the list items
    * @tparam A the list element type
    * @return a list
    */
  def apply[A](items: A*): LinkedList[A] = {
    val newList = DoublyLinkedList.empty[A]
    for (el <- items) {
      newList += el
    }
    newList
  }

  private def areEquals[A](xs: DoublyLinkedList[A], ys: DoublyLinkedList[A]): Boolean = {
    if (xs.isEmpty && !ys.isEmpty)
      false
    else if (!xs.isEmpty && ys.isEmpty)
      false
    else if (xs.isEmpty && ys.isEmpty)
      true
    else {
      val xit = xs.elements
      val yit = ys.elements
      false
    }
  }
}