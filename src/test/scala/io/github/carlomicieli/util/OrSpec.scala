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
package io.github.carlomicieli.util

import io.github.carlomicieli.dst.immutable.List
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

  "getOrElse" should "return the contained value for Good" in {
    good.getOrElse("default") shouldBe good.get
    bad.getOrElse("default") shouldBe "default"
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

  "it" should "convert a Bad value to a None" in {
    bad.toMaybe shouldBe None
  }

  "it" should "convert a Good value to a Just" in {
    good.toMaybe shouldBe Just("answer")
  }

  "foreach" should "do nothing for Bad values" in {
    var n = 0
    bad.foreach { x => n = n + 1 }
    n shouldBe 0
  }

  "foreach" should "apply the function to Good values" in {
    var n = 0
    good foreach { x => n = n + x.length }
    n shouldBe "answer".length
  }

  "zip" should "combine two Or values" in {
    good zip good shouldBe Good(("answer", "answer"))
    good zip bad shouldBe Bad(List(42))
    bad zip good shouldBe Bad(List(42))
    bad zip bad shouldBe Bad(List(42, 42))
  }

  "exists" should "apply a predicate to Good values" in {
    good exists { _.startsWith("a") } shouldBe true
    bad exists { _.startsWith("a") } shouldBe false
  }

  "swap" should "exchange Good with Bad, and viceversa" in {
    good.swap shouldBe Bad("answer")
    bad.swap shouldBe Good(42)
  }
}

trait GoodOrBadValues {
  def good: String Or Int = Good("answer")
  def bad: String Or Int = Bad(42)
}
