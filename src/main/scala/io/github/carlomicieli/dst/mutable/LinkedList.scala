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
import io.github.carlomicieli.util.{Good, Bad, Or}

/**
 * It represented a Linked List.
 * @tparam A
 */
final class LinkedList[A] extends Container[A] {

  private type NodePair = Option[(Node, Node)]

  private var head: Node = Nil
  private var last: Node = Nil

  /**
   * Return the head of the list, if any.
   * This is an O(1) operation.
   *
   * @return
   */
  def headOption: Option[A] = head.toOption

  /**
   * Return the last element of the list, if any.
   * This is an O(1) operation.
   *
   * @return
   */
  def lastOption: Option[A] = last.toOption

  /**
   * Insert a new key in front of the list
   * This is an O(1) operation.
   *
   * @param key
   */
  def addFront(key: A): Unit = {
    head = head +? newNode(key)
    last = last ?? head
  }

  def keys: Iterable[A] = new Iterable[A] {
    def iterator: Iterator[A] = new Iterator[A] {
      private var curr: Node = LinkedList.this.head
      def hasNext: Boolean = !curr.isNil
      def next(): A = {
        val key = curr.key
        curr = curr.next
        key
      }
    }
  }

  /**
   * Append a new key to the end of the list
   * This is an O(1) operation.
   *
   * @param key
   */
  def addBack(key: A): Unit = {
    val node = newNode(key)
    last match {
      case Nil => last = node
      case n@LNode(_, next) =>
        n.next = node
        last = node
    }

    head = head ?? last
  }

  /**
   * Apply a function to all list elements, for its side effect.
   * @param f
   * @tparam U
   */
  def forEach[U](f: (A) => U): Unit = {
    @annotation.tailrec
    def loop(n: Node): Unit = n match {
      case Nil => ()
      case LNode(k, next) =>
        f(k)
        loop(next)
    }

    loop(head)
  }

  def findFirst(p: A => Boolean): Option[A] = {
    @annotation.tailrec
    def loop(curr: Node): Option[A] = curr match {
      case Nil => None
      case LNode(k, next) =>
        if (p(k))
          Some(k)
        else
          loop(next)
    }

    loop(head)
  }

  /**
   * Return the list of the size.
   * This is a O(n) operation.
   *
   * @return
   */
  def size: Int =
    fold(0)((x, c) => c + 1)

  /**
   * Checks whether the list contains the given key.
   * This is a O(n) operation.
   *
   * @param x
   * @return
   */
  def contains(x: A): Boolean = {
    @annotation.tailrec
    def loop(n: Node): Boolean = n match {
      case Nil => false
      case LNode(k, next) =>
        if (k == x) true
        else loop(next)
    }

    loop(head)
  }

  /**
   * Checks whether the list is empty.
   * This is a O(1) operation.
   *
   * @return
   */
  def isEmpty: Boolean = head.isNil

  def mkString(sep: String): String = {
    @annotation.tailrec
    def loop(n: Node, acc: String): String = n match {
      case Nil => acc
      case LNode(k, next) => loop(next, acc + sep + k.toString)
    }

    head match {
      case Nil => ""
      case LNode(k, next) => loop(next, k.toString)
    }
  }

  def fold[B](z: B)(op: (A, B) => B): B = {
    @annotation.tailrec
    def loop(curr: Node, acc: B): B = curr match {
      case LNode(v, next) => loop(next, op(v, acc))
      case Nil => acc
    }

    loop(head, z)
  }

  def removeHead: (A, LinkedList[A]) Or EmptyLinkedListException = {
    (head, last) match {
      case (Nil, Nil) =>
        Bad(new EmptyLinkedListException)
      case (h, l) if h == l =>
        head = Nil
        last = Nil
        Good((h.key, this))
      case (LNode(k, next), _) =>
        head = next
        Good((k, this))
    }
  }

  def remove(key: A): Boolean = {
    (head, last) match {
      case (Nil, Nil) => false
      case (h, l) if h == l =>
        head = Nil
        last = Nil
        true
      case (LNode(k, next), _) if k == key =>
        head = next
        true
      case (_, _) =>
        @annotation.tailrec
        def findNodeBefore(curr: Node, np: NodePair): NodePair = {
          curr match {
            case Nil => None
            case LNode(k, next) =>
              if (k == key) np
              else
                findNodeBefore(next, Some((next, curr)))
          }
        }

        findNodeBefore(head, None) match {
          case None => false
          case Some((n, prev)) =>
            prev.next = n.next
            if (n.next.isNil)
              last = prev
            true
        }
    }
  }

  def toList: List[A] = {
    def loop(n: Node, acc: List[A]): List[A] = n match {
      case Nil => acc
      case LNode(k, next) =>
        loop(next, acc ::: List(k))
    }

    loop(head, List())
  }

  override def toString = s"List(${mkString(", ")})"

  private def newNode(key: A): Node = new LNode(key)

  private sealed trait Node {
    def key: A
    var next: Node
    def isNil: Boolean

    def toOption: Option[A] = this match {
      case Nil => None
      case LNode(k, _) => Some(k)
    }

    def +?(that: Node): Node = this match {
      case Nil         => that
      case LNode(_, _) =>
        that.next = this
        that
    }

    def ??(that: Node): Node = this match {
      case Nil         => that
      case LNode(_, _) => this
    }
  }

  private case class LNode(key: A, var next: Node) extends Node {
    def this(k: A) {
      this(k, Nil)
    }

    def isNil = false
  }

  private object Nil extends Node {
    def key = throw new NoSuchElementException("LinkedList.key: Nil node")

    def next = throw new NoSuchElementException("LinkedList.key: Nil node")
    def next_=(x: Node): Unit = {}

    def isNil = true
  }
}

object LinkedList {

  def apply[A](items: A*): LinkedList[A] = {
    val list = LinkedList.empty[A]
    if (items.isEmpty)
      list
    else {
      for (i <- items.reverse) {
        list.addFront(i)
      }
      list
    }
  }

  def empty[A]: LinkedList[A] = new LinkedList[A]
}

class EmptyLinkedListException extends Exception("LinkedList is empty")