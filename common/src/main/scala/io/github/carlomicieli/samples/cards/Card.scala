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

package io.github.carlomicieli.samples.cards

case class Card(rank: Rank, suit: Suit) {
  override def toString = {
    val r = rank match {
      case Number(num) => num.toString
      case _           => rank.toString
    }
    s"Card($r of $suit)"
  }
}

object Card {
  implicit object CardOrdering extends Ordering[Card] {
    def compare(x: Card, y: Card): Int = (x, y) match {
      case (Card(r1, s1), Card(r2, s2)) if s1 == s2 =>
        r1.value.compare(r2.value)
      case (Card(_, s1), Card(_, s2)) =>
        s1.value.compare(s2.value)
    }
  }
}