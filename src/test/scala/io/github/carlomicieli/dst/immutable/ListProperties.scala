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
package io.github.carlomicieli.dst.immutable

import io.github.carlomicieli.test.AbstractPropSpec
import org.scalacheck.{Arbitrary, Gen}
import Gen._
import org.scalacheck.Prop.{forAll, BooleanOperators}

class ListProperties extends AbstractPropSpec {

  property("+: increase the list length by 1") {
    check(forAll { (x: Int, xs: List[Int]) =>
      val res = x +: xs
      res.length === xs.length + 1
    })
  }

  property("+: add the new element as the list head") {
    check(forAll { (x: Int, xs: List[Int]) =>
      val ys = x +: xs
      ys.head === x
    })
  }

  property("map: resulting list has the same length as the original list") {
    check(forAll { (xs: List[Int]) =>
      xs.map(_ * 2).length === xs.length
    })
  }

  property("map: compose two function") {
    check(forAll { (xs: List[Int]) =>
      val f: Int => Int = _ * 2
      val g: Int => Int = _ + 42

      xs.map(f.andThen(g)) === xs.map(f).map(g)
    })
  }

  property("flatMap: resulting list has the same length as the original list") {
    check(forAll { (xs: List[Int]) =>
      xs.flatMap(x => List(2 * x)).length === xs.length
    })
  }

  property("zip: resulting list is long as the shortest one") {
    check(forAll { (xs: List[Int], ys: List[Int]) =>
      val zs = xs zip ys
      zs.length === math.min(xs.length, ys.length)
    })
  }

  property("zip: resulting list must have the pair of original head elements as head") {
    check(forAll { (xs: List[Int], ys: List[Int]) =>
      (xs.nonEmpty && ys.nonEmpty) ==> {
        val zs = xs zip ys
        (xs.head, ys.head) === zs.head
      }
    })
  }

  property("zip: resulting list contains original elements") {
    check(forAll { (xs: List[Int], ys: List[Int]) =>
      val zs = xs zip ys
      val len = zs.length

      zs.map(_._1) === xs.take(len)
      zs.map(_._2) === ys.take(len)
    })
  }

  property("take: take all the list elements produce the same list as result") {
    check(forAll { (xs: List[Int]) =>
      xs.take(xs.length) === xs
    })
  }

  property("take: take elements until the list have them") {
    check(forAll(intLists, posNum[Int]) { (xs: List[Int], n: Int) =>
      xs.take(n).length === math.min(n, xs.length)
    })
  }

  property("drop: drop 0 elements leave the list unchanged") {
    check(forAll { (xs: List[Int]) =>
      xs.drop(0) eq xs
    })
  }

  property("drop: dropping more elements that the list length produce the empty list") {
    check(forAll { (xs: List[Int]) =>
      val ys = xs.drop(1 + xs.length)
      ys.isEmpty
    })
  }

  property("reverse: apply reverse twice produce the same list") {
    check(forAll { (xs: List[Int]) =>
      xs.reverse.reverse === xs
    })
  }

  property("++: the resulting length is the sum of the two original lists") {
    check(forAll { (xs: List[Int], ys: List[Int]) =>
      (xs ++ ys).length === xs.length + ys.length
    })
  }

  def intLists(implicit a: Arbitrary[List[Int]]): Gen[List[Int]] = a.arbitrary
}
