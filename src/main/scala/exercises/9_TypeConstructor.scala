package exercises

object TypeConstructorExercises {
  /**
   *  1) In the previous chapter we tried to implement 'Functor' as a typeClass but we noticed that using a typeClass was not enough. We needed 'Functor' to
   *  reason about the type inside a typeClass. This is exactly what a type constructor lets us do!
   *
   *  Try to define the 'Functor' trait again, but this time as a type constructor.
   *
   *  Remember: the 'Functor' trait is a generalization of all types that can hold the 'map' function.
   */
  trait Functor[F[_]] {
    def map[A,B](fa: F[A])(f: A => B): F[B]
  }

  /**
   *  2) (OPTIONAL) Define a 'Parser' trait. Which operations would be useful for a parser?
   *
   *  Tips:
   *  - we should be able to run a parser and get either the expected output or an exception (run)
   *  - we should be able to turn any value into a parser (unit)
   *  - we should be able to turn any Parser[A] into a Parser[B] (map, flatMap)
   *  - we should be able to merge any two parsers into a new parser (map2)
   *  - we should be able to transform a list of parsers into a parser of list (sequence)
   *  - (optional) we should be able to transform a parser of list into a list of parsers (traverse)
   */


  /**
   *  3) An other common generalization is called 'Applicative'. This trait defines two functions: 'unit' and 'map2'.
   *
   *  Define the 'Applicative' trait.
   */

  /**
   *  Consider this implementation...
   */







  /**
   *  Consider this JSON definition...
   */
  trait JSON
  case object JNull extends JSON
  case class JNumber(get: Double) extends JSON
  case class JString(get: String) extends JSON
  case class JBool(get: Boolean) extends JSON
  case class JArray(get: IndexedSeq[JSON]) extends JSON
  case class JObject(get: Map[String, JSON]) extends JSON

  /**
   * And this parser...
   */
  /*
  trait Parser[+A,ParseError] {
    def run(input: String): Either[ParseError,A]
  }
   */


  /*
  trait Functor[F[_]] {
    def map[A,B](fa: F[A])(f: A => F[B]): F[B]
  }

   */
}



