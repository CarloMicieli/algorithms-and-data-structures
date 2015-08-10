package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.util.Good
import org.scalatest.{Matchers, FlatSpec}

class LinkedListSpec extends FlatSpec with Matchers with SampleLists {
  "An empty list" should "have size 0" in {
    val empty = LinkedList.empty[Int]
    empty.isEmpty should be(true)
    empty.size should be(0)
  }

  "Adding an item in front" should "increase size by 1" in {
    val l = emptyList
    l.addFront(42)
    l.size should be(1)
  }

  "Adding an item at the back" should "increase size by 1" in {
    val l = emptyList
    l.addBack(42)
    l.size should be(1)
  }

  "Adding an item" should "change head and last elements" in {
    val l = emptyList
    l.addFront(42)
    l.headOption should be(Some(42))
    l.lastOption should be(Some(42))
  }

  "Adding items to a LinkedList" should "modify the list" in {
    val l = emptyList
    l.addFront(3)
    l.addBack(4)
    l.addFront(2)
    l.addBack(5)
    l.addFront(1)
    l.size should be(5)
  }

  "A linked list" should "be constructed from a varargs parameter" in {
    val l = LinkedList(1, 2, 3, 4, 5, 6)
    l.size should be(6)
  }

  "mkString" should "produce a String from list elements" in {
    emptyList.toString should be("List()")
    singletonList.toString should be("List(42)")
    numbersList.toString should be("List(1, 2, 3, 4, 5, 6)")
  }

  "forEach" should "apply a function to all elements" in {
    var sum = 0
    numbersList.forEach { n => sum = sum + n }
    sum should be(21)
  }

  "contains()" should "return false when the list is empty" in {
    emptyList.contains(99) should be(false)
  }

  "contains()" should "return false when the element is not in the list" in {
    numbersList.contains(99) should be(false)
  }

  "contains()" should "return true when the element is in the list" in {
    numbersList.contains(2) should be(true)
  }

  "remove()" should "remove an element at the middle" in {
    val l = numbersList
    val res = l.remove(4)

    res should be(true)
    l.toString should be("List(1, 2, 3, 5, 6)")
  }

  "remove()" should "remove the head from a list" in {
    val l = numbersList
    val res = l.remove(1)
    res should be(true)
    l.headOption should be(Some(2))
  }

  "remove()" should "remove the last from a list" in {
    val l = numbersList
    val res = l.remove(6)

    res should be(true)
    l.lastOption should be(Some(5))
  }

  "remove()" should "remove the only element of a list" in {
    val l = singletonList
    l.remove(42)

    l.headOption should be(None)
    l.lastOption should be(None)
  }

  "toList()" should "convert a linked list to a scala List" in {
    val l = numbersList.toList
    l should be(List(1, 2, 3, 4, 5, 6))
  }

  "removeHead()" should "remove the head from a list" in {
    val l = numbersList
    val Good((h, l2)) = l.removeHead
    h should be(1)
    l2.size should be(5)
  }
}

trait SampleLists {
  def emptyList = LinkedList.empty[Int]
  def singletonList = LinkedList(42)
  def numbersList = LinkedList(1, 2, 3, 4, 5, 6)
}
