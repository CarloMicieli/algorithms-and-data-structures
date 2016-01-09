/*
 *                       __                  __
 *      ______________ _/ /___ _      ____ _/ /___ _____
 *     / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *    (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 *   /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                         /____/
 *  Copyright (c) 2016 the original author or authors.
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
package io.github.carlomicieli.fp.samples.strings

import io.github.carlomicieli.test.AbstractPropertySpec
import org.scalacheck.Gen
import CaesarCipher._

class CaesarCipherProperties extends AbstractPropertySpec {

  property("int2char and char2int") {
    forAll { (ch: Char) =>
      char2int(int2char(ch)) shouldBe ch
    }
  }

  property("isLower: returns true for upper case letters") {
    forAll(Gen.alphaUpperChar) { c: Char =>
      isLower(c) shouldBe false
    }
  }

  property("isLower: returns false for upper case letters") {
    forAll(Gen.alphaLowerChar) { c: Char =>
      isLower(c) shouldBe true
    }
  }

  property("int2char") {
    forAll(Gen.choose(0, 25)) { x: Int =>
      def isLowerCaseLetter(c: Char) = c >= 'a' && c <= 'z'

      val c = int2char(x)
      isLowerCaseLetter(c) shouldBe true
    }
  }

  property("char2int") {
    forAll(Gen.choose('a', 'z')) { c: Char =>

      char2int(c) should (be >= 0 and be < 26)
    }
  }

  property("shift") {
    forAll(Gen.choose(-99999, 99999), Gen.choose('a', 'z')) { (x: Int, ch: Char) =>
      shift(x, shift(-x, ch)) shouldBe ch
    }
  }

  property("shift: shift by 0 leave the char unchanged") {
    forAll { (c: Char) =>
      shift(0, c) shouldBe c
    }
  }

  property("percentage: for 2 positive numbers is between 0 and 100 whenever x < y") {
    forAll(Gen.posNum[Int], Gen.posNum[Int]) { (x: Int, y: Int) =>
      whenever(y != 0 && x < y) {
        percent(x, y) should (be > 0.0f and be <= 100.0f)
      }
    }
  }

  property("percentage: for when x > y") {
    forAll(Gen.posNum[Int], Gen.posNum[Int]) { (x: Int, y: Int) =>
      whenever(y != 0 && x > y) {
        percent(x, y) should (be > 100.0f)
      }
    }
  }

  property("freqs: the sum of all chars sequence must be 100%") {
    forAll(nonEmptyLowerCharsList) { cs: List[Char] =>
      val sum: Float = freqs(cs).map { case (_, v) => v }.sum
      sum should be (100.0f +- 0.1f)
    }
  }

  property("freqs: returns the frequency of a letter") {
    forAll(Gen.alphaLowerChar, Gen.choose(1, 10)) { (ch: Char, fr: Int) =>
      val cs = generate((fr, ch))
      val letterFreqs = freqs(cs)

      val (_, charFreq) = letterFreqs(index(ch))
      charFreq should be (100.0f +- 0.01f)
    }
  }

  property("freqs: returns the frequency of letters") {
    forAll(Gen.alphaLowerChar, Gen.alphaLowerChar, Gen.choose(1, 10)) { (c1: Char, c2: Char, fr: Int) =>
      whenever(c1 != c2) {
        val cs = generate((fr, c1), (fr * 2, c2))
        val letterFreqs = freqs(cs)

        val (_, freq1) = letterFreqs(index(c1))
        val (_, freq2) = letterFreqs(index(c2))

        freq2 / freq1 should be(2.0f +- 0.01f)
      }
    }
  }

  property("rotate: don't change the list length") {
    forAll { (n: Int, xs: List[Int]) =>
      val ys = rotate(n, xs)
      ys.length shouldBe xs.length
    }
  }

  property("rotate: rotate by the list length leave the list unchanged") {
    forAll { xs: List[Char] =>
      rotate(xs.length, xs) shouldBe xs
    }
  }

  property("rotate: the new head is the element at position n") {
    forAll(Gen.posNum[Int], Gen.nonEmptyListOf[Char](Gen.alphaNumChar)) { (n: Int, cs: List[Char]) =>
      whenever(n < cs.length) {
        val ocs = rotate(n, cs)
        val newIndex = n

        cs(newIndex) shouldBe ocs.head
      }
    }
  }

  property("positions: should find the index for the element") {
    forAll { (x: Int, lhs: List[Int], rhs: List[Int]) =>
      val xs = lhs ::: List(x) ::: rhs
      positions(x, xs).contains(lhs.length) shouldBe true
    }
  }

  property("positions: list with all elements") {
    forAll(Gen.nonEmptyListOf(Gen.const(42))) { (xs: List[Int]) =>
      positions(42, xs) shouldBe xs.indices.toList
    }
  }

  property("positions: return list empty when element is not found") {
    forAll(Gen.nonEmptyListOf(Gen.const(42))) { (xs: List[Int]) =>
      positions(99, xs) shouldBe List.empty[Int]
    }
  }

  def generate(p: (Int, Char)*): List[Char] = {
    p.flatMap { case (fr, ch) => List.fill(fr)(ch) }.toList
  }
  def index(c: Char): Int = c - 'a'.toInt
}

