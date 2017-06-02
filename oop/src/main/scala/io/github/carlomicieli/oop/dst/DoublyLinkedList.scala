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

    def isSentinel: Boolean
    def isEmpty: Boolean

    def valueOption: Option[A] = {
      if (isEmpty)
        None
      else
        Some(key)
    }

    def toOption: Option[Node] = if (isEmpty) None else Some(this)
  }

  private class ValueNode(var key: A) extends Node {
    def this(v: A, p: Node, n: Node) {
      this(v)
      prev = p
      next = n
    }

    override var prev: Node = Nil
    override var next: Node = Nil

    override def isSentinel: Boolean = false
    override def isEmpty: Boolean = false
  }

  private class Sentinel extends Node {
    override var prev: Node = Nil
    override var next: Node = Nil

    override def isSentinel: Boolean = true
    override def isEmpty: Boolean = true

    override def key: A = throw new NoSuchElementException("Sentinel cell: no value found")
    override def key_=(x: A): Unit = {}
  }

  private object Nil extends Node {
    def next = throw new NoSuchElementException("Nil node has no next")

    def next_=(n: Node): Unit = {}

    def prev = throw new NoSuchElementException("Nil node has no next")

    def prev_=(n: Node): Unit = {}

    override def isSentinel: Boolean = false
    override def isEmpty: Boolean = true

    override def key: A = throw new NoSuchElementException("Nil cell: no value found")
    override def key_=(x: A): Unit = {}
  }

  private val firstNode: Node = new Sentinel
  private val lastNode: Node = new Sentinel

  override def headOption: Option[A] = firstNode.next.valueOption

  override def lastOption: Option[A] = lastNode.prev.valueOption

  override def isEmpty: Boolean = firstNode.next.isEmpty

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
      loop(firstNode)(z)(f)
    }
  }

  override def foldRight[B](z: B)(f: (A, B) => B): B = {
    if (isEmpty) {
      z
    } else {
      var acc: B = z
      var node = lastNode.prev
      while (!node.isEmpty) {
        acc = f(node.key, acc)
        node = node.prev
      }
      acc
    }
  }

  override def insert(key: A)(implicit ord: Ordering[A]): Unit = ???

  override def mkString(sep: String, start: String, end: String): String = {
    val elements = if (isEmpty) {
      ""
    } else {
      loop(firstNode.next)(firstNode.next.key.toString)((str, x) => s"$str$sep$x")
    }

    s"$start$elements$end"
  }

  override def elements: Iterable[A] = new Iterable[A] {
    override def iterator: Iterator[A] = new Iterator[A] {
      var curr: Node = DoublyLinkedList.this.firstNode
      override def hasNext: Boolean = !curr.isEmpty
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
        if (n.prev.isSentinel)
          firstNode.next = n.next
        if (n.next.isSentinel)
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

  override def clear(): Unit = {
    firstNode.next = Nil
    lastNode.prev = Nil
  }

  private def loop[B](startingNode: Node)(z: B)(f: (B, A) => B): B = {
    startingNode match {
      case Nil => z
      case _ =>
        var acc: B = z
        var node = startingNode.next
        while (!node.isEmpty) {
          acc = f(acc, node.key)
          node = node.next
        }
        acc
    }
  }

  private def findNode(p: (A) => Boolean): Option[Node] = {
    if (isEmpty) {
      None
    } else {
      val nodeMatches: Node => Boolean = n => n.valueOption.exists(p)

      var node = firstNode.next
      while (!node.isEmpty && !nodeMatches(node)) {
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
}