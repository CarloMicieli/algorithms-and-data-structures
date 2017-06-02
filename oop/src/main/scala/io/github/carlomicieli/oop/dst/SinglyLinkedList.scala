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

import scala.util.{ Success, Failure, Try }

private[this] class SinglyLinkedList[A] extends LinkedList[A] {

  private var headNode: Node = Nil
  private var lastNode: Node = Nil

  sealed trait Node {
    def key: A
    def key_=(x: A): Unit
    def next: Node
    def next_=(n: Node): Unit
    def isEmpty: Boolean
    def nonEmpty: Boolean = !isEmpty

    def nextOrElse(n: => Node): Node = if (isEmpty) n else next
  }

  case class ListNode(var key: A, var next: Node) extends Node {
    def isEmpty = false
  }

  case object Nil extends Node {
    def key = throw new NoSuchElementException("Sentinel node has no key")
    def key_=(x: A): Unit = {}
    def next = throw new NoSuchElementException("Sentinel node has no next")
    def next_=(n: Node): Unit = {}
    def isEmpty = true
  }

  override def isEmpty: Boolean = headNode.isEmpty
  override def nonEmpty: Boolean = headNode.nonEmpty

  override def headOption: Option[A] = if (headNode.nonEmpty) Some(headNode.key) else None
  override def lastOption: Option[A] = if (lastNode.nonEmpty) Some(lastNode.key) else None

  override def prepend(el: A): Unit = {
    headNode = ListNode(el, headNode)
    if (lastNode.isEmpty)
      lastNode = headNode
  }

  override def append(el: A): Unit = {
    val newNode = ListNode(el, Nil)
    if (lastNode.nonEmpty)
      lastNode.next = newNode

    lastNode = newNode

    if (headNode.isEmpty)
      headNode = newNode
  }

  override def insert(key: A)(implicit ord: Ordering[A]): Unit = {
    if (isEmpty) {
      prepend(key)
    } else {
      import Ordered._

      var prev: Node = headNode
      var curr: Node = headNode
      while (curr.nonEmpty && curr.key < key) {
        prev = curr
        curr = curr.next
      }
      prev.next = ListNode(key, curr)
    }
  }

  override def elements: Iterable[A] = new Iterable[A] {
    override def iterator: Iterator[A] = new Iterator[A] {
      var curr: Node = SinglyLinkedList.this.headNode
      override def hasNext: Boolean = curr.nonEmpty
      override def next(): A = {
        val k = curr.key
        curr = curr.next
        k
      }
    }
  }

  override def foreach[U](f: (A) => U): Unit = {
    var curr = headNode
    while (curr.nonEmpty) {
      f(curr.key)
      curr = curr.next
    }
  }

  override def length: Int = {
    foldLeft(0)((len, _) => len + 1)
  }

  override def foldLeft[B](z: B)(f: (B, A) => B): B = {
    loop(headNode)(z)(f)
  }

  override def foldRight[B](z: B)(f: (A, B) => B): B = {
    val elements = foldLeft(Stack.empty[A])((st, x) => { st push x; st })
    var acc = z
    while (elements.nonEmpty) {
      acc = f(elements.pop(), acc)
    }
    acc
  }

  override def mkString(sep: String, start: String = "", end: String = ""): String = {
    val itemsString = if (isEmpty) ""
    else {
      loop(headNode.next)(headNode.key.toString)((str, x) => str + sep + x)
    }
    s"$start$itemsString$end"
  }

  private def loop[B](start: Node)(z: B)(f: (B, A) => B): B = {
    var acc = z
    var curr = start
    while (curr.nonEmpty) {
      acc = f(acc, curr.key)
      curr = curr.next
    }
    acc
  }

  override def contains(key: A): Boolean = {
    find(_ == key).isDefined
  }

  override def find(p: (A) => Boolean): Option[A] = {
    findNode(p) map { case (curr, _) => curr.key }
  }

  private def findNode(p: (A) => Boolean): Option[(Node, Node)] = {
    if (isEmpty) {
      None
    } else {
      var found: Option[(Node, Node)] = None
      var curr: Node = headNode
      var prev: Node = Nil
      while (curr.nonEmpty && found.isEmpty) {
        found = if (p(curr.key)) Some((curr, prev)) else None
        curr = curr.next
        prev = prev.nextOrElse(headNode)
      }
      found
    }
  }

  override def remove(key: A): Boolean = {
    findNode(_ == key) match {
      case None => false
      case Some((curr, Nil)) =>
        headNode = curr.nextOrElse(Nil)
        lastNode = if (headNode.isEmpty) Nil else lastNode
        true
      case Some((curr, prev)) =>
        prev.next = curr.nextOrElse(Nil)
        true
    }
  }

  def update[B, C](newKey: A)(implicit ev: (A) => (B, C)): Boolean = {
    findNode(_._1 == newKey._1) match {
      case None =>
        append(newKey)
        true
      case Some((curr, _)) =>
        curr.key = newKey
        false
    }
  }

  def removeHead(): Try[A] = {
    (headNode, lastNode) match {
      case (Nil, Nil) =>
        Failure(new EmptyLinkedListException("removeHead"))
      case (h, l) if h == l =>
        headNode = Nil
        lastNode = Nil
        Success(h.key)
      case (ListNode(k, next), _) =>
        headNode = next
        Success(k)
    }
  }

  override def clear(): Unit = {
    headNode = Nil
    lastNode = Nil
  }
}

object SinglyLinkedList {
  /** Creates an empty singly linked list.
    * @tparam A the list element type
    * @return an empty list
    */
  def empty[A]: LinkedList[A] = new SinglyLinkedList[A]

  /** Creates a singly linked list with the given items.
    * @param items the list items
    * @tparam A the list element type
    * @return a list
    */
  def apply[A](items: A*): LinkedList[A] = {
    val newList = SinglyLinkedList.empty[A]
    for (el <- items) {
      newList.append(el)
    }
    newList
  }
}