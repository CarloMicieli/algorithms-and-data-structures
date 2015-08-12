package io.github.carlomicieli.samples

sealed trait Suit {
  def id: Int
}
case object Clubs    extends Suit {
  val id = 0
}
case object Diamonds extends Suit {
  val id = 1
}
case object Hearts   extends Suit {
  val id = 2
}
case object Spades   extends Suit {
  val id = 3
}

sealed trait Rank {
  def value: Int
}
case object Ace   extends Rank {
  val value = 1
}
case class  Number(value: Int) extends Rank
case object Jack  extends Rank {
  val value = 11
}
case object Queen extends Rank {
  val value = 12
}
case object King  extends Rank {
  val value = 13
}

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
        s1.id.compare(s2.id)
    }
  }
}

object Deck {
  def toArray: Array[Card] = {
    val array = toList.toArray
    def swap(x: Int, y: Int): Unit = {
      val tmp = array(x)
      array(x) = array(y)
      array(y) = tmp
    }
    val N = array.length
    val rnd = scala.util.Random
    for (i <- 0 until N) {
      val j = rnd.nextInt(N - i)
      swap(i, j)
    }

    array
  }

  def toList: List[Card] = {
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