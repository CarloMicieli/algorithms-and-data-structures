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
