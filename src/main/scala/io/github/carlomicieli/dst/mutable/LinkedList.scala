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
package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.Container
import io.github.carlomicieli.util.{Or, Bad, Good}

/**
 * It represents a mutable singly linked list.
 * @tparam A the list element type
 */
trait LinkedList[A] extends Container[A] {

  /**
   * `O(1)` Returns the first element (if any) from this list
   * @return the list head
   */
  def head: A = headOption.get

  /**
   * `O(1)` Returns the last element (if any) from this list
   *
   * This method is assuming the linked list implementation will store a pointer to the last element.
   * @return the list tail
   */
  def last: A = lastOption.get

  /**
   * `O(1)` Optionally returns the first element (if any) from this list
   * @return the list head
   */
  def headOption: Option[A]

  def removeHead(): (A, LinkedList[A]) Or EmptyLinkedListException

  /**
   * `O(1)` Optionally returns the last element (if any) from this list.
   *
   * This method is assuming the linked list implementation will store a pointer to the last element.
   * @return the list tail
   */
  def lastOption: Option[A]

  /**
   * `O(1)` Insert a new element in the front of the list.
   * @param key the element to be inserted
   */
  def +=(key: A): Unit = addBack(key)

  def ++=(keys: A*): Unit = {
    for (k <- keys)
      addBack(k)
  }

  /**
   * `O(n)` Remove the element with the given `key` from the list, if exists.
   * @param key the key to be removed
   */
  def -=(key: A): Boolean = remove(key)

  /**
   * `O(1)` Checks whether this list is empty.
   * @return `true` if the list does not contain any element, `false` otherwise.
   */
  def isEmpty: Boolean

  /**
   * `O(1)` Checks whether this list is not empty.
   * @return `true` if the list does contain elements, `false` otherwise.
   */
  def nonEmpty: Boolean

  /**
   * `O(1)` Insert a new element in the front of the list.
   * @param el the new element
   */
  def addFront(el: A): Unit

  /**
   * `O(1)` Insert a new element to the back of the list.
   * @param el the new element
   */
  def addBack(el: A): Unit

  /**
   * `O(n)` Inserts the element into the list at the first position where it
   * is less than or equal to the next element.
   *
   * @param key the element to insert
   * @param ord
   * @return
   * @usecase def insert(key: A): Unit
   */
  def insert(key: A)(implicit ord: Ordering[A]): Unit

  /**
   *
   * @usecase def foreach(f: A => Unit): Unit
   * @param f
   * @tparam U
   */
  def foreach[U](f: A => U): Unit

  /**
   * `O(n)` Returns the number of elements in the list
   * @return the number of elements.
   */
  def length: Int

  /**
   * `O(n)` Applies a binary operator `f` to a start value and all elements of this list, going left to right.
   * @param z the start value
   * @param f the binary operator
   * @tparam B the output type
   * @return the result of inserting `op` between consecutive elements
   */
  def foldLeft[B](z: B)(f: (B, A) => B): B

  /**
   * `O(n)` Applies a binary operator `f` to a start value and all elements of this list, going right to left.
   * @param z the start value
   * @param f the binary operator
   * @tparam B the output type
   * @return the result of inserting `op` between consecutive elements
   */
  def foldRight[B](z: B)(f: (A, B) => B): B

  /**
   * Displays all elements of this list in a string.
   * @param sep
   * @return
   */
  def mkString(sep: String): String = mkString(sep)

  /**
   * Displays all elements of this list in a string.
   * @param sep
   * @param start
   * @param end
   * @return
   */
  def mkString(sep: String, start: String = "", end: String = ""): String

  /**
   * Returns an [[scala.collection.Iterable]] for this list elements.
   * @return the elements Iterable
   */
  def elements: Iterable[A]

  /**
   * `O(n)` Checks whether the given key is contained in the list.
   * @param key the key to find
   * @return `true` if the list contains `key`; `false` otherwise.
   */
  def contains(key: A): Boolean

  /**
   * `O(n)` Finds the first element of this list satisfying a predicate, if any.
   * @param p the predicate used to test elements.
   * @return an option value containing the first element in the list that satisfies `p`,
   *         or `None` if none exists.
   */
  def find(p: A => Boolean): Option[A]

  /**
   * `O(n)` Remove the element with the given `key` from the list, if exists.
   *
   * In case more element with the same `key` are in the list, this method
   * will remove only the first occurrence.
   *
   * @param key the key to remove
   * @return `true` if an element was removed; `false` otherwise.
   */
  def remove(key: A): Boolean

  def update[B, C](key: A)(implicit ev: A => (B, C)): Boolean

  def zip[B](that: LinkedList[B]): Iterable[(A, B)] = {
    this.elements.zip(that.elements)
  }

  override def toString: String = mkString(", ", "[", "]")
}

object LinkedList {
  def empty[A]: LinkedList[A] = new SinglyLinkedList[A]
  def apply[A](items: A*): LinkedList[A] = {
    val l = LinkedList.empty[A]
    for { el <- items } l.addBack(el)
    l
  }

  def unapplySeq[A](list: LinkedList[A]): Option[Seq[A]] = {
    if (list.isEmpty) None
    else {
      val seq = list.foldRight(Seq.empty[A])((x, xs) => x +: xs)
      Some(seq)
    }
  }

  private class SinglyLinkedList[A] extends LinkedList[A] {

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
      def key_=(x: A) = {}
      def next = throw new NoSuchElementException("Sentinel node has no next")
      def next_=(n: Node) = {}
      def isEmpty = true
    }

    override def isEmpty: Boolean = headNode.isEmpty
    override def nonEmpty: Boolean = headNode.nonEmpty

    override def headOption: Option[A] = if (headNode.nonEmpty) Some(headNode.key) else None
    override def lastOption: Option[A] = if (lastNode.nonEmpty) Some(lastNode.key) else None

    override def addFront(el: A): Unit = {
      headNode = ListNode(el, headNode)
      if (lastNode.isEmpty)
        lastNode = headNode
    }

    override def addBack(el: A): Unit = {
      val newNode = ListNode(el, Nil)
      if (lastNode.nonEmpty)
        lastNode.next = newNode

      lastNode = newNode

      if (headNode.isEmpty)
        headNode = newNode
    }

    override def insert(key: A)(implicit ord: Ordering[A]): Unit = {
      import Ordered._

      findNode(_ > key) match {
        case None =>
          val newNode = ListNode(key, Nil)
          headNode = newNode
          lastNode = newNode
        case Some((curr, prev)) =>
          val newNode = ListNode(key, curr)
          prev.next = newNode
          if (prev.isEmpty)
            headNode = newNode
      }
    }

    override def elements: Iterable[A] = new Iterable[A] {
      override def iterator: Iterator[A] = new Iterator[A] {
        var curr: Node = SinglyLinkedList.this.headNode
        override def hasNext = curr.nonEmpty
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
      foldLeft(0)((len, x) => len + 1)
    }

    override def foldLeft[B](z: B)(f: (B, A) => B): B = {
      loop(headNode)(z)(f)
    }

    override def foldRight[B](z: B)(f: (A, B) => B): B = {
      val elements = foldLeft(Stack.empty[A])((st, x) => { st push x ; st })
      var acc = z
      while (elements.nonEmpty) {
        acc = f(elements.pop, acc)
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
      findNode(p) match {
        case None => None
        case Some((curr, _)) => Some(curr.key)
      }
    }

    private def findNode(p: (A) => Boolean): Option[(Node, Node)] = {
      if (isEmpty) None
      else {
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
        case Some((curr, prev)) => {
          prev.next = curr.nextOrElse(Nil)
          true
        }
      }
    }

    def update[B, C](newKey: A)(implicit ev: (A) => (B, C)): Boolean = {
      findNode(_._1 == newKey._1) match {
        case None =>
          addBack(newKey)
          true
        case Some((curr, _)) =>
          curr.key = newKey
          false
      }
    }

    def removeHead(): (A, LinkedList[A]) Or EmptyLinkedListException = {
      (headNode, lastNode) match {
        case (Nil, Nil) =>
          Bad(new EmptyLinkedListException)
        case (h, l) if h == l =>
          headNode = Nil
          lastNode = Nil
          Good((h.key, this))
        case (ListNode(k, next), _) =>
          headNode = next
          Good((k, this))
      }
    }
  }
}

class EmptyLinkedListException extends Exception("LinkedList is empty")