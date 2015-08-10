package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.Container

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

  def removeHead: (A, LinkedList[A]) = {
    (head, last) match {
      case (Nil, Nil) =>
        throw new NoSuchElementException("LinkedList.removeHead: list is empty")
      case (h, l) if h == l =>
        head = Nil
        last = Nil
        (h.key, this)
      case (LNode(k, next), _) =>
        head = next
        (k, this)
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
