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

package io.github.carlomicieli.samples.cards

class Deck private (c: Array[Card]) {
  private val cards = c

  def shuffle(): Unit = {
    def swap(x: Int, y: Int): Unit = {
      val tmp = cards(x)
      cards(x) = cards(y)
      cards(y) = tmp
    }
    val N = cards.length
    val rnd = new scala.util.Random
    for (i <- 0 until N) {
      val j = rnd.nextInt(N - i)
      swap(i, j)
    }
  }

  override def toString = c.mkString("Deck(", ", ", ")")
}

object Deck {
  def apply(): Deck = {
    new Deck(cardsList.toArray)
  }

  private def cardsList: List[Card] = {
    for {
      s <- suits
      r <- ranks
    } yield Card(r, s)
  }

  private def ranks = {
    val nums = (2 to 10).map { Number }.toList
    Ace :: nums ::: List(Jack, Queen, King)
  }
  private def suits = List(Clubs, Diamonds, Hearts, Spades)
}
