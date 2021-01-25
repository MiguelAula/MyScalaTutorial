package solutions.TypeClasses

import scala.util.Try

/**
 *  4) Define a 'Parser' trait. A parser is a typeClass that contains a run method that receives a String input and returns a Result of type A, which can be a
 *  Success or a Failure. In case of success we would be able to retrieve a value of type A, in case of failure we should be able to get the error.
 *
 *  Which operations would be useful for a parser? To start off define only the function signatures.
 *
 *  Which ones can be implemented without knowing anything about the Parser implementation? In other words, which functions can be implemented just by
 *  using other functions that you just defined?
 *
 *  Tips:
 *  - we should be able to run a printer and get the expected output (run)
 *  - we should be able to turn any value into a parser of that value (unit)
 *  - we should be able to transform the Parser content type (map, flatMap)
 *  - we should be able to merge any two parsers into a new parser (map2)
 *  - we should be able to transform a list of parsers into a parser of list (sequence)
 *  - (optional) we should be able to transform a parser of list into a list of parser (traverse)
 */
trait Parser[A] {
  def run(input: String): Result[A]
  def map[B](f: A => B): Parser[B] = this.flatMap(a => Parser.unit(f(a)))
  def flatMap[B](f: A => Parser[B]): Parser[B] =
    (input: String) => this.run(input) match {
      case Success(get, _) => f(get).run(input)
      case f: Failure => f
    }

  //for testing
  def runAndPrint(input: String): Unit = println(run(input))
}
object Parser {
  def unit[A](a: A): Parser[A] = new Parser[A] {
    override def run(input: String): Result[A] = Success(a,input.length)
  }
  def sequence[A](l: List[Parser[A]]): Parser[List[A]] = l.foldLeft(unit(List(): List[A]))((acc, p) => map2(acc,p)((l, pr) => pr :: l))
  def map2[A,B,C](p1: Parser[A], p2: => Parser[B])(f: (A,B) => C): Parser[C] = p1.flatMap(a => p2.map(b => f(a,b)))
}

sealed trait Result[+A] //if you take the sealed keyword out, watch what happens to the autocompletion of a Result match :)
case class Success[+A](get: A, charsConsumed: Int) extends Result[A]
case class Failure(get: Throwable) extends Result[Nothing]

/**
 *  4.2) Come up with an implementation for your Parser trait.
 *
 *  Once you are done, run the appropriate test to check if your implementation is valid (you will need to change the import on the test file to point to
 *  your Parser implementation.
 *
 *  Note: the names of the methods need to EXACTLY match AT LEAST the ones proposed on the 'tips' section or else the test suite will fail.
 */

object Main {
  val intParser: Parser[Int] =
  //Note: here we use scala.Try to make a handmade .toIntOption using our own handmade Option package. If we were using scala's Option, we could simply do: int.toIntOption
    (input: String) => Try(Some(input.toInt)).getOrElse(None) match {
      case Some(value) => Success(value, input.length)
      case None => Failure(new Throwable(s"$input can't be casted to int"))
    }

  val listParser: Parser[List[String]] =
    (input: String) =>
      if (input.matches("List\\(((.*[^,])|(.*[^,],.*[^,])*)\\)")) Success(input.stripPrefix("List(").stripSuffix(")").split(',').toList,input.length)
      else Failure(new Throwable(s"$input does not have List format"))

  def main(args: Array[String]): Unit = {
    intParser.runAndPrint("3453")
    intParser.runAndPrint("ff32f23")
    val isPairParser = intParser.map(n => n % 2 == 0)
    isPairParser.runAndPrint("3")
    isPairParser.runAndPrint("2")

    listParser.runAndPrint("List(324235,235,236,4,63)")
    listParser.runAndPrint("List()")
    listParser.runAndPrint("List(")
  }
}
