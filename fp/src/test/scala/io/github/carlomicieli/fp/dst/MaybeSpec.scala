/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
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

import io.github.carlomicieli.fp.typeclasses.{ Ord, Eq, Show, Ordering }
import io.github.carlomicieli.test.AbstractSpec

class MaybeSpec extends AbstractSpec with MaybeFixture {

  describe("A Maybe") {
    describe("Maybe") {
      it("should create new Good values") {
        val just = Maybe(42)
        just shouldBe Just(42)
      }
    }

    describe("isEmpty") {
      it("should return false for Just values") {
        just42.isEmpty shouldBe false
      }

      it("should return true for None values") {
        none.isEmpty shouldBe true
      }
    }

    describe("isDefined") {
      it("should return true for Just values") {
        just42.isDefined shouldBe true
      }

      it("should return false for None values") {
        none.isDefined shouldBe false
      }
    }

    describe("get") {
      it("should extract the contained value in Just values") {
        just42.get shouldBe 42
      }

      it("should throw an exception for None values") {
        the[NoSuchElementException] thrownBy {
          none.get
        } should have message "Maybe.get: a value doesn't exist"
      }
    }

    describe("getOrElse") {
      it("should provide the contained value in Just values") {
        just42.getOrElse(-1) shouldBe 42
      }

      it("should provided the default for None values") {
        none.getOrElse(-1) shouldBe -1
      }
    }

    describe("orElse") {
      it("should return a new value if it's a None") {
        none.orElse(just42) shouldBe just42
      }

      it("should return the same value, if it's a Just value") {
        just42.orElse(Just(99)) shouldBe just42
        just42 should be theSameInstanceAs just42
      }
    }

    describe("orElseThrow") {
      it("should return the contained value if it's Just") {
        just42.orElseThrow(new Exception("my exception")) shouldBe 42
      }

      it("should throw the provided exception for None values") {
        the[Exception] thrownBy {
          none.orElseThrow(new Exception("my exception"))
        } should have message "my exception"
      }
    }

    describe("mapOrElse") {
      it("should return the orElse value for None values") {
        none.mapOrElse(_ * 2)(Just(999)) shouldBe Just(999)
      }

      it("should return the value after the function has been applied for Just values") {
        just42.mapOrElse(_ * 2)(Just(999)) shouldBe Just(84)
      }
    }

    describe("fold") {
      it("should return the orElse value for None values") {
        none.fold(_ * 2)(999) shouldBe 999
      }

      it("should return the value after the function has been applied for Just values") {
        just42.fold(_ * 2)(999) shouldBe 84
      }
    }

    describe("foreach") {
      it("should apply a function f to Just values for its side effects") {
        var res = 0
        just42.foreach(x => res = x + 1)
        res shouldBe 43
      }

      it("should do nothing for None values") {
        var res = 0
        none.foreach(x => res = res + 1)
        res shouldBe 0
      }
    }

    describe("map") {
      it("should apply the function to Just values") {
        just42.map(_ * 2) shouldBe Just(84)
      }

      it("should simply produce a None, if it's a None") {
        none.map(_ * 2) shouldBe None
      }
    }

    describe("flatMap") {
      it("should apply the function to Just values") {
        just42.flatMap(x => Maybe(x * 2)) shouldBe Just(84)
      }

      it("should do nothing, if it's a None value") {
        none.flatMap(x => Maybe(x * 2)) shouldBe None
      }
    }

    describe("toString") {
      it("should produce a string representation for Just values") {
        just42.toString shouldBe "Just(42)"
      }

      it("should produce a string representation for None values") {
        none.toString shouldBe "None"
      }
    }

    describe("toGood") {
      it("should convert a Just to a Good value") {
        just42.toGood("it's bad") shouldBe Good(42)
      }

      it("should convert a None to a Bad value, using the provided value") {
        none.toGood("it's bad") shouldBe Bad("it's bad")
      }
    }

    describe("toList") {
      it("should convert a Just value to the singleton list") {
        just42.toList shouldBe List(42)
      }

      it("should convert a None value to the empty list") {
        none.toList shouldBe List()
      }

      it("should flatten list values, keeping only the Just") {
        val xs = List(Just(1), None, None, Just(4), Just(5))
        xs.flatten shouldBe List(1, 4, 5)
      }
    }

    describe("catMaybes") {
      it("should extract only the Just values from a list") {
        val list = List(Just(1), None, None, Just(4), Just(5), None)
        Maybe.catMaybes(list) shouldBe List(1, 4, 5)
      }
    }

    describe("filter") {
      it("should return this value, if it's Good and match the predicate") {
        just42.filter(_ % 2 == 0) should be theSameInstanceAs just42
      }

      it("should return None, if it's Good and doesn't match the predicate") {
        just42.filter(_ % 2 != 0) shouldBe none
      }

      it("should return None, if it's None") {
        none.filter(_ % 2 != 0) shouldBe none
      }
    }

    describe("show") {
      it("should return 'None' for None values") {
        Show[Maybe[Int]].show(none) shouldBe "None"
      }

      it("should return 'Just( )' for Just values") {
        Show[Maybe[Int]].show(just42) shouldBe "Just(42)"
      }
    }

    describe("eq") {
      it("should be an instance of the eq type class") {
        Eq[Maybe[Int]].eq(just42, just42) shouldBe true
        Eq[Maybe[Int]].eq(none, none) shouldBe true
        Eq[Maybe[Int]].eq(none, just42) shouldBe false
        Eq[Maybe[Int]].eq(just42, none) shouldBe false
      }
    }

    describe("ord") {
      it("should be an instance of the Ord type class") {
        val ord = Ord[Maybe[Int]]
        ord.compare(none, none) shouldBe Ordering.EQ
        ord.compare(none, just42) shouldBe Ordering.LT
        ord.compare(Maybe(41), Maybe(42)) shouldBe Ordering.LT
        ord.compare(Maybe(41), none) shouldBe Ordering.GT
        ord.compare(Maybe(42), Maybe(41)) shouldBe Ordering.GT
      }
    }
  }
}

trait MaybeFixture {
  val none = Maybe.empty[Int]
  val just42 = Maybe(42)
}
