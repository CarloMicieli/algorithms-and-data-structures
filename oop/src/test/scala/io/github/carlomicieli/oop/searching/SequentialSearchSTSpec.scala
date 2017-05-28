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

package io.github.carlomicieli.oop.searching

import org.scalatest.{Matchers, FlatSpec}

class SequentialSearchSTSpec extends FlatSpec with Matchers with SampleSymbolTables {
  "Empty sequential search st" should "be emtpy and have size 0" in {
    val st = SequentialSearchST.empty[String, Int]
    st.size should be(0)
    st.isEmpty should be(true)
  }

  "Adding a key/value pair to sequential search ST" should "change its size" in {
    val st = emptySeqST
    st("one") = 1
    st.isEmpty should be(false)
    st.size should be(1)
  }

  "Adding more distinct key/value pair" should "change the ST size" in {
    val st = emptySeqST
    st("one") = 1
    st("two") = 2
    st("four") = 4
    st.size should be(3)
  }

  "Searching a key not in the ST" should "fail" in {
    val st = emptySeqST
    val r = intercept[NoSuchElementException] {
      st("not_found")
    }
  }

  "Searching a key inserted in the ST" should "return its value" in {
    val st = numbersSeqST
    st("one") should be(1)
    st.get("one") should be(Some(1))
  }

  "Adding the same key twice to the ST" should "replace its value" in {
    val st = emptySeqST
    st("one") = 2
    st("one") = 1
    st("one") should be(1)
    st.size should be(1)
  }

  "Removing a key/value pair from ST" should "remove its value" in {
    val st = numbersSeqST
    st.delete("three")
    st.get("three") should be(None)
  }

  "contains()" should "check whether the given key exists in the ST" in {
    val st = numbersSeqST
    st.contains("three") should be(true)
    st.contains("not found") should be(false)
  }

  "keys()" should "return the list of keys from ST" in {
    val st = numbersSeqST
    st.keys.toList should be(List("four", "three", "two", "one"))
  }
}

trait SampleSymbolTables {
  def emptySeqST = SequentialSearchST.empty[String, Int]
  def numbersSeqST = {
    val st = emptySeqST
    st("one") = 1
    st("two") = 2
    st("three") = 3
    st("four") = 4
    st
  }
}