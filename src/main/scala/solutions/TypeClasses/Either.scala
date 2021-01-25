package solutions.TypeClasses

/**
 *  Define the 'Either' trait.
 *
 *  Define the unit, map and flatMap functions for this trait.
 */
sealed trait Either[+E,+A] {
  def map[B](f: A => B): Either[E,B] = this.flatMap(x => Right(f(x)))
  def flatMap[EE >: E,B](f: A => Either[EE,B]): Either[EE,B] = this match {
    case Right(value) => f(value)
    case Left(err) => Left(err)
  }
}
object Either {
  def unit[A](a: A): Either[Nothing,A] = Right(a)
}
final case class Right[+V](value: V) extends Either[Nothing,V]
final case class Left[+E](err: E) extends Either[E,Nothing]
