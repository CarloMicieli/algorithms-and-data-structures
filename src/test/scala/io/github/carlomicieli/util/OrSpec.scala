package io.github.carlomicieli.util

import org.scalatest.{Matchers, FlatSpec}

class OrSpec extends FlatSpec with Matchers with GoodOrBadValues {
  "A Good value" should "return the value it contains" in {
    good.get should be("answer")
  }

  "isGood" should "return true whether the value is Good" in {
    good.isGood should be(true)
    bad.isGood should be(false)
  }

  "A Bad value" should "throw an exception when asked for its value" in {
    val r = intercept[NoSuchElementException] {
      bad.get
    }
  }

  "isBad" should "return true whether the value is Bad" in {
    good.isBad should be(false)
    bad.isBad should be(true)
  }

  "map" should "be Good biased for Or values" in {
    good.map(_.toUpperCase) should be(Good("ANSWER"))
    bad.map(_.toUpperCase) should be(bad)
  }

  "flatMap" should "be Good biased for Or values" in {
    good.flatMap(s => Good(s.toUpperCase)) should be(Good("ANSWER"))
    bad.flatMap(s => Good(s.toUpperCase)) should be(Bad(42))
  }

  "orElse" should "return a default for Bad values" in {
    good.orElse("default") should be(good)
    bad.orElse("default") should be(Bad("default"))
  }

  "toString" should "produce string representations for Or values" in {
    good.toString should be("Good(answer)")
    bad.toString should be("Bad(42)")
  }
}

trait GoodOrBadValues {
  def good: String Or Int = Good("answer")
  def bad: String Or Int = Bad(42)
}
