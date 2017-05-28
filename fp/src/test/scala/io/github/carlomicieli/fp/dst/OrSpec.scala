/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                   /____/
 * Copyright (c) 2017 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.carlomicieli.fp.dst

import io.github.carlomicieli.test.AbstractSpec

class OrSpec extends AbstractSpec with OrValuesFixture {

  describe("Or value") {
    describe("get") {
      it("should return the value contained in a 'Good'") {
        good.get shouldBe "answer"
      }

      it("should throw an exception for 'Bad' values") {
        the[NoSuchElementException] thrownBy {
          bad.get
        } should have message "Or.get: is empty"
      }
    }

    describe("getOrElse") {
      it("should return the contained value for Good") {
        good.getOrElse("default") shouldBe "answer"
      }

      it("should return the default value for Bad values") {
        bad.getOrElse("default") shouldBe "default"
      }
    }

    describe("orElse") {
      it("should return the same Good value, if this is a Good") {
        good.orElse("default") should be theSameInstanceAs good
      }

      it("should return a Bad containing the default, if this is Bad") {
        bad.orElse("default") shouldBe Bad("default")
      }
    }

    describe("isGood") {
      it("should return 'true' for Good values") {
        good.isGood shouldBe true
      }

      it("should return 'false' for Bad values") {
        bad.isGood shouldBe false
      }
    }

    describe("isBad") {
      it("should return 'true' for Bad values") {
        bad.isBad shouldBe true
      }

      it("should return 'false' for Good values") {
        good.isBad shouldBe false
      }
    }

    describe("map") {
      it("should apply the function to the value contained in Good values") {
        good.map(_.toUpperCase) shouldBe Good("ANSWER")
      }

      it("should return the same Bad instance, when f is applied to Bad values") {
        val result = bad.map(_.toUpperCase)
        result shouldBe bad
        result shouldBe theSameInstanceAs(bad)
      }
    }

    describe("flatMap") {
      it("should apply the function to the contained value in Good values") {
        good.flatMap(s => Good(s.toUpperCase)) shouldBe Good("ANSWER")
      }

      it("should return the same bad value, when applying the function to Bad values") {
        bad.flatMap(s => Good(s.toUpperCase)) should be theSameInstanceAs bad
      }
    }

    describe("toString") {
      it("should produce a string representation for Good values") {
        good.toString shouldBe "Good(answer)"
      }

      it("should produce a string representation for Bad values") {
        bad.toString shouldBe "Bad(42)"
      }
    }

    describe("toMaybe") {
      it("should convert a Good to a Just value") {
        good.toMaybe shouldBe Just("answer")
      }

      it("should convert a Bad to a None value") {
        bad.toMaybe shouldBe None
      }
    }

    describe("foreach") {
      it("should do nothing for Bad values") {
        var n = 0
        bad.foreach { x => n = n + 1 }
        n shouldBe 0
      }

      it("should apply the function to Good values") {
        var n = 0
        good foreach { x => n = n + x.length }
        n shouldBe "answer".length
      }
    }

    describe("zip") {
      it("should combine two Good values to a Good") {
        good zip good shouldBe Good(("answer", "answer"))
      }

      it("should combine Good and Bad values to a Bad") {
        good zip bad shouldBe Bad(List(42))
        bad zip good shouldBe Bad(List(42))
      }

      it("should combine two Bad values in a Bad") {
        bad zip bad shouldBe Bad(List(42, 42))
      }
    }

    describe("exists") {
      it("should apply a predicate to Good values") {
        good exists { _.startsWith("a") } shouldBe true
      }

      it("should apply a predicate to Bad values") {
        bad exists { _.startsWith("a") } shouldBe false
      }
    }

    describe("swap") {
      it("should exchange Good with Bad") {
        good.swap shouldBe Bad("answer")
      }

      it("should exchange Bad with Good") {
        bad.swap shouldBe Good(42)
      }
    }
  }
}

trait OrValuesFixture {
  val good: String Or Int = Good("answer")
  val bad: String Or Int = Bad(42)
}