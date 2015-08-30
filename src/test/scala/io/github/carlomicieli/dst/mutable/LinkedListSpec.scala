package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.test.AbstractTestSpec
import io.github.carlomicieli.util.Good

class LinkedListSpec extends AbstractTestSpec with SampleLists {
  "An empty list" should "have size 0" in {
    val empty = LinkedList.empty[Int]
    empty.isEmpty shouldBe true
    empty.length shouldBe 0
  }

  "Adding an item in front" should "increase size by 1" in {
    val l = emptyList
    l.addFront(42)
    l.length shouldBe 1
  }

  "Adding an item at the back" should "increase size by 1" in {
    val l = emptyList
    l.addBack(42)
    l.length shouldBe 1
  }

  "Adding an item" should "change head and last elements" in {
    val l = emptyList
    l.addFront(42)
    l.headOption shouldBe Some(42)
    l.lastOption shouldBe Some(42)
  }

  "Adding items to a LinkedList" should "modify the list" in {
    val l = emptyList
    l.addFront(3)
    l.addBack(4)
    l.addFront(2)
    l.addBack(5)
    l.addFront(1)
    l.length shouldBe 5
  }

  "A linked list" should "be constructed from a varargs parameter" in {
    val l = LinkedList(1, 2, 3, 4, 5, 6)
    l.length shouldBe 6
  }

  "mkString" should "produce a String from list elements" in {
    emptyList.toString shouldBe "[]"
    singletonList.toString shouldBe "[42]"
    numbersList.toString shouldBe "[1, 2, 3, 4, 5, 6]"
  }

  "forEach" should "apply a function to all elements" in {
    var sum = 0
    numbersList.foreach { n => sum = sum + n }
    sum shouldBe 21
  }

  "contains()" should "return false when the list is empty" in {
    emptyList.contains(99) shouldBe false
  }

  "contains()" should "return false when the element is not in the list" in {
    numbersList.contains(99) shouldBe false
  }

  "contains()" should "return true when the element is in the list" in {
    numbersList.contains(2) shouldBe true
  }

  "remove()" should "remove an element at the middle" in {
    val l = numbersList
    val res = l.remove(4)

    res shouldBe true
    l.toString shouldBe "[1, 2, 3, 5, 6]"
  }

  "remove()" should "remove the head from a list" in {
    val l = numbersList
    val res = l.remove(1)
    res shouldBe true
    l.headOption shouldBe Some(2)
  }

  "remove()" should "remove the last from a list" in {
    val l = numbersList
    val res = l.remove(6)

    res shouldBe true
    l.lastOption shouldBe Some(6)
  }

  "remove()" should "remove the only element of a list" in {
    val l = singletonList
    l.remove(42)

    l.headOption shouldBe None
    l.lastOption shouldBe None
  }

//  "toList()" should "convert a linked list to a scala List" in {
//    val l = numbersList.toList
//    l should be(List(1, 2, 3, 4, 5, 6))
//  }

  "removeHead()" should "remove the head from a list" in {
    val l = numbersList
    val Good((h, l2)) = l.removeHead()
    h should be(1)
    l2.elements.toList.length shouldBe 5
  }

  "findFirst()" should "return the first key that matches the predicate" in {
    val l = numbersList
    l.find {
      _ == 5
    } shouldBe Some(5)
    l.find {
      _ == 9
    } shouldBe None
  }

  "keys()" should "produce an Iterable from the linked list keys" in {
    val l = numbersList
    l.elements.size shouldBe l.length
    l.elements.mkString(",") shouldBe "1,2,3,4,5,6"
  }
}

trait SampleLists {
  def emptyList = LinkedList.empty[Int]
  def singletonList = LinkedList(42)
  def numbersList = LinkedList(1, 2, 3, 4, 5, 6)
}
