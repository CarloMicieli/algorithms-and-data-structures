package io.github.carlomicieli.dst.immutable

import io.github.carlomicieli.test.AbstractTestSpec

class ListSpec extends AbstractTestSpec with TestLists {
  "An empty list" should "have length equals to 0" in {
    val empty = List.empty[Int]
    empty.length shouldBe 0
  }

  "An empty list" should "return true when asked whether is empty or not" in {
    val empty = List.empty[Int]
    empty.isEmpty shouldBe true
    empty.nonEmpty shouldBe false
  }

  "A list" should "return the number of elements it contains" in {
    val list = 1 +: 2 +: 3 +: Nil
    list.length shouldBe 3
    list.isEmpty shouldBe false
    list.nonEmpty shouldBe true
  }

  "Equals" should "check whether two lists are equals" in {
    val l1 = numbersList
    val l2 = numbersList
    val l3 = emptyList
    val l4 = emptyList

    l1 == l2 shouldBe true
    l1 == l3 shouldBe false
    l3 == l2 shouldBe false
    l3 == l4 shouldBe true
  }

  "Reversing a list" should "produce the list in reverse order" in {
    val l1 = numbersList
    val l2 = l1.reverse
    l2 shouldBe List(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
  }

  "toString()" should "produce a string representation of a list" in {
    emptyList.toString shouldBe "[]"
    numbersList.toString shouldBe "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]"
  }

  "foldLeft" should "apply a binary operator to list elements, from left to right" in {
    val res = numbersList.foldLeft(100)(_ - _)
    res shouldBe 45
  }

  "foldRight" should "apply a binary operator to list elements, from right to left" in {
    val res = numbersList.foldRight(100)(_ - _)
    res shouldBe 95
  }

  "map" should "apply a function to list elements" in {
    val l2 = numbersList.map { _ *  2}
    l2 shouldBe List(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
  }

  "interperse" should "interperse a value between list elements" in {
    val l = List('a', 'b', 'c', 'd', 'e').intersperse('-')
    l shouldBe List('a', '-', 'b', '-', 'c', '-', 'd', '-', 'e')
  }

  "concat" should "produce a new list, concatenating the two lists elements" in {
    val l1 = List(5, 6, 7, 8, 9, 10)
    val l2 = List(1, 2, 3, 4, 5)

    val l3 = l1 ++ l2
    l3 shouldBe List(5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5)
  }

  "unfoldRight" should "produce a new list from a seed value and a function" in {
    val l = List.unfoldRight(1)(n => if (n < 10) Some((s"N($n)", n + 1)) else None)
    l shouldBe List("N(9)", "N(8)", "N(7)", "N(6)", "N(5)", "N(4)", "N(3)", "N(2)", "N(1)")
  }

  "take" should "take the first n elements from the list" in {
    val l1 = emptyList take 5
    val l2 = numbersList take 5

    l1 shouldBe List()
    l2 shouldBe List(1, 2, 3, 4, 5)
  }

  "drop" should "remove the first n elements from the list" in {
    val l1 = emptyList drop 5
    val l2 = numbersList drop 5

    l1 shouldBe List()
    l2 shouldBe List(6, 7, 8, 9, 10)
  }
}

trait TestLists {
  def emptyList: List[Int] = List()
  def numbersList: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  def list[A](items: A*): List[A] = List(items: _*)
}
