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

package io.github.carlomicieli.oop.samples

import io.github.carlomicieli.oop.dst.MinPQ
import io.github.carlomicieli.oop.searching.SymbolTable
import io.github.carlomicieli.samples.Words

object FrequencyCounter {
  case class WordCount(word: String, count: Int)

  implicit val workCountOrd = new Ordering[WordCount] {
    def compare(x: WordCount, y: WordCount): Int = x.count.compare(y.count)
  }

  def apply(filename: String, minLength: Int)(st: SymbolTable[String, Int]): List[WordCount] = {
    val words = Words.fromFile(filename)
    for (w <- words if w.length() >= minLength) {
      if (st.contains(w))
        st(w) = st(w) + 1
      else
        st(w) = 1
    }

    val m = 10
    val minPQ = MinPQ[WordCount](m + 1)

    st.keys.foreach { k =>
      val wc = WordCount(k, st(k))
      minPQ.insert(wc)
      if (minPQ.size > m) {
        minPQ.deleteMin()
      }
    }

    minPQ.toList.sorted
  }
}