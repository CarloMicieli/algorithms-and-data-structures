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

  property("tails: the first element is the original list") {
    check(forAll { (xs: List[Int]) =>
      xs.tails.head === xs
    })
  }

  property("tails: the last element is the empty list") {
    check(forAll { (xs: List[Int]) =>
      xs.tails.last === List()
    })
  }

  property("tails: resulting list has length equal original length + 1") {
    check(forAll { (xs: List[Int]) =>
      xs.tails.length === xs.length + 1
    })
  }

  property("intersperse: length increased") {
    check(forAll { (x: Int, xs: List[Int]) =>
      (xs.length > 1) ==> {
        xs.intersperse(x).length === (xs.length + xs.length - 1)
      }
    })
  }

  property("intersperse: contains element from the original list") {
    check(forAll { (x: Int, xs: List[Int]) =>
      val isOdd = (x: (Int, Int)) => x._2 % 2 == 1
      val ys = xs.intersperse(x)
      ys.zipWithIndex.filter(isOdd).map(_._1) === xs
    })
  }

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

  property("foldLeft: apply function to list elements") {
    check(forAll { (xs: List[Int]) =>
      xs.foldLeft(42)(_ + _) === xs.sum + 42
    })
  }

  property("foldRight: Nil and cons recreate the original list") {
    check(forAll { (xs: List[Int]) =>
      xs.foldRight(List.empty[Int])(_ +: _) === xs
    })
  }

  property("foldLeft: Nil and cons recreate the original list reversed") {
    check(forAll { (xs: List[Int]) =>
      xs.foldLeft(List.empty[Int])((ys, y) => y +: ys) === xs.reverse
    })
  }

  property("foldRight: apply function to list elements") {
    check(forAll { (xs: List[Int]) =>
      xs.foldRight(42)(_ + _) === xs.sum + 42
    })
  }

  property("foldLeft and foldRight produce the same result for associative operations") {
    check(forAll { (xs: List[Int]) =>
      xs.foldLeft(0)(_ + _) === xs.foldRight(0)(_ + _)
    })
  }

  property("isEmpty: true when length = 0") {
    check(forAll { (xs: List[Int]) =>
      xs.isEmpty === (xs.length == 0)
    })
  }

  property("nonEmpty: true when length != 0") {
    check(forAll { (xs: List[Int]) =>
      xs.nonEmpty === (xs.length != 0)
    })
  }

  property("isEmpty and nonEmpty cannot be both true for the same list") {
    check(forAll { (xs: List[Int]) =>
      xs.isEmpty === !xs.nonEmpty
    })
  }

  property("elem: find the element in a list") {
    check(forAll { (x: Int, yz: List[Int], zs: List[Int]) =>
      val xs = yz ++ (x +: zs)
      xs elem x
    })
  }

  property("filter: resulting list length") {
    check(forAll { (xs: List[Int]) =>
      val ys = xs.filter(_ % 2 == 0)
      ys.length <= xs.length
    })
  }

  property("filter and filterNot: consider all elements") {
    check(forAll { (xs: List[Int]) =>
      val isEven = (x: Int) => x % 2 == 0
      xs.filter(isEven).length + xs.filterNot(isEven).length === xs.length
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

  property("append: the resulting length is the sum of the two original lists") {
    check(forAll { (xs: List[Int], ys: List[Int]) =>
      (xs ++ ys).length === xs.length + ys.length
    })
  }

  property("append and ++ are equivalent") {
    check(forAll { (xs: List[Int], ys: List[Int]) =>
      (xs ++ ys) === (xs append ys)
    })
  }

  property("replicate: produce a list with length n") {
    check(forAll(posNum[Int]) { (n: Int) =>
      val ys = List.replicate(n)(42)
      ys.length === n
    })
  }

  property("flatten: resulting list has length with the sum of nested lists") {
    check(forAll { (xss: List[List[Int]]) =>
      val ys = xss.flatten
      ys.length === xss.map(_.length).sum
    })
  }

  property("all and any prop") {
    check(forAll { (xs: List[Int]) =>
      val p       = (x: Int) => x % 2 == 0
      val negateP = (x: Int) => !p(x)
      xs.all(p) === !xs.any(negateP)
    })
  }

  property("sort: produce sorted list") {
    check(forAll { (xs: List[Int]) =>
      xs.nonEmpty ==> {
        val ys = xs.sort
        ys.zip(ys.tail).all(p => p._1 <= p._2)
      }
    })
  }

  property("span: sum of the two lists length is the same as the original") {
    check(forAll { (xs: List[Int]) =>
      val (ys, zs) = xs.span(_ % 2 == 0)
      ys.length + zs.length === xs.length
    })
  }

  property("span: divide list using the predicate") {
    check(forAll { (xs: List[Int]) =>
      val isEven = (x: Int) => x % 2 == 0

      val (ys, zs) = xs.span(isEven)
      ys.all(isEven) === true
      zs.any(isEven) === false
    })
  }

  property("splitAt: sum of resulting lists is equal to the original list length") {
    check(forAll { (x: Int, xs: List[Int]) =>
      val (ys, zs) = xs.splitAt(x)
      ys.length + zs.length === xs.length
    })
  }

  property("splitAt: length") {
    check(forAll { (x: Int, xs: List[Int]) =>
      val suffixLen = math.min(xs.length, math.max(0, x))
      val (ys, zs) = xs.splitAt(x)
      ys.length <= suffixLen
      zs.length <= (xs.length - suffixLen)
    })
  }

  def intLists(implicit a: Arbitrary[List[Int]]): Gen[List[Int]] = a.arbitrary
}
