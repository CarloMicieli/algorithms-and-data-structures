package io.github.carlomicieli.samples

sealed trait Expr
case class Add(l: Expr, r: Expr) extends Expr
case class Mul(l: Expr, r: Expr) extends Expr
case class Val(value: Int) extends Expr
case class Var(name: String) extends Expr

object Expr {
  type Bind = (String, Int)

  def show(exp: Expr, lev: Int = 0): String = {
    def parentes(s: String): String =
      if (lev == 0) s else s"($s)"

    exp match {
      case Add(l, r)  => parentes(s"${show(l, lev + 1)} + ${show(r, lev + 1)}")
      case Mul(l, r) => parentes(s"${show(l, lev + 1)} * ${show(r, lev + 1)}")
      case Val(v)     => v.toString
      case Var(name)  => name
    }
  }

  def calc(exp: Expr, env: List[Bind]): Int = exp match {
    case Add(l, r)  => calc(l, env) + calc(r, env)
    case Mul(l, r) => calc(l, env) * calc(r, env)
    case Val(v)     => v
    case Var(name)  =>
      env.filter(b => b._1 == name).map(b => b._2) match {
        case x :: _ => x
        case List()  => throw new NoSuchElementException(s"No bind found for variable '$name'")
      }
  }
}