package io.github.carlomicieli.samples

sealed trait Suit {
  def value: Int = this match {
    case Clubs => 1
    case Diamonds => 2
    case Hearts => 3
    case Spades => 4
  }
}
case object Clubs    extends Suit
case object Diamonds extends Suit
case object Hearts   extends Suit
case object Spades   extends Suit

sealed trait Rank {
  def value: Int = this match {
    case Ace => 1
    case Number(n) => n
    case Jack => 11
    case Queen => 12
    case King => 13
  }
}
case object Ace   extends Rank
case class  Number(n: Int) extends Rank
case object Jack  extends Rank
case object Queen extends Rank
case object King  extends Rank

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

class Deck private(c: Array[Card]) {
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