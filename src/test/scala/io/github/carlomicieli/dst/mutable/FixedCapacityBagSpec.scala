package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.Bag
import org.scalatest.{Matchers, FlatSpec}

class FixedCapacityBagSpec extends FlatSpec with Matchers with SampleBags {
  "An empty Bag" should "have size equals to 0" in {
    val bag = emptyFixedCapBag(16)
    bag.size should be(0)
    bag.isEmpty should be(true)
  }

  "Adding an element to a bag" should "increase its size" in {
    val bag = emptyFixedCapBag(16)
    bag.add(1)
    bag.add(2)
    bag.size should be(2)
    bag.isEmpty should be(false)
  }
  
  it should "check whether an element is contained in the bad" in {
    val bag = fixedCapBag(1, 2, 3, 4)
    bag.contains(1) should be(true)
    bag.contains(9) should be(false)
  }
}

trait SampleBags {
  def emptyFixedCapBag(n: Int): Bag[Int] = FixedCapacityBag[Int](n)
  
  def fixedCapBag(items: Int*): Bag[Int] = {
    val bag = FixedCapacityBag[Int](items.length)
    for (i <- items)
      bag.add(i)
    bag
  }
}
