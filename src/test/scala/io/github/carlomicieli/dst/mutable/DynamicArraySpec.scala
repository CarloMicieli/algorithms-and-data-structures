package io.github.carlomicieli.dst.mutable

import org.scalatest.{Matchers, FlatSpec}

class DynamicArraySpec extends FlatSpec with Matchers {
  "An empty DynamicArray" should "have a length" in {
    val a = DynamicArray.empty[Int](10)
    a.length should be(10)
  }

  "Setting element to DynamicArray" should "be index based" in {
    val a = DynamicArray.empty[Int](10)
    a(0) = 1
    a(1) = 2
    a(0) should be(1)
    a(1) should be(2)
  }

  "resize operation" should "change the DynamicArray size" in {
    val a = DynamicArray.empty[Int](5)
    val b = a.resize(2.0)
    b.length should be(10)
  }

  "shift" should "slide all elements from starting point in DynamicArray" in {
    val a = DynamicArray(1, 2, 3, 4, 5)
    a.shift(1, 1)

    a.toString should be("[1, 2, 2, 3, 4]")
  }

  "insert()" should "insert the new element when the predicate is true" in {
    val a = DynamicArray(1, 2, 4, 5, 0)
    val inserted = a.insert(3)(_ <= _)
    inserted should be(true)
    a.toString should be("[1, 2, 3, 4, 5]")
  }

  "DynamicArray" should "growth and shrink" in {
    val a = DynamicArray.empty[Int](10)
    a.length should be(10)

    val b = a.expand
    b.length should be(15)

    val c = a.shrink
    c.length should be(6)
  }
}
