package solutions

object TypeClassesExercises {
  /**
   *  1) In the previous chapter we noticed that we needed to know the initial value for a type to be able to implement reduce in terms of foldLeft for
   *  any type [A]. We needed a zero value.
   *
   *  Define a typeClass for [A] called 'Monoid'. It should contain a single method called 'zero'.
   */
  trait Monoid[A] {
    def zero: A

    //1.7)
    def op(x: A,y: A): A
  }

  /**
   * 1.2) Define at least two implicit values for the Monoid typeClass (Int,String)
   */
  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    override def zero: Int = 0
    //1.7)
    override def op(x: Int, y: Int): Int = x + y
  }
  implicit val strMonoid: Monoid[String] = new Monoid[String] {
    override def zero: String = ""
    //1.7)
    override def op(x: String, y: String): String = x + y
  }

  /**
   *  1.3) Now use this typeClass to implement reduceFL (you can change the given signature)
   */
  def reduceFL[A: Monoid](l: List[A])(f: (A,A) => A): A = l.foldLeft(implicitly[Monoid[A]].zero)(f)

  /**
   *  1.4) The 'Monoid' typeClass allows you to implement reduceFL for any simple data type. But what if A is by itself a typeClass like List[A]?
   *  Try to define an implicit value for the 'Monoid' typeClass for List[Int].
   */
  implicit val listIntMonoid: Monoid[List[Int]] = new Monoid[List[Int]] {
    override def zero: List[Int] = List()
    //1.7)
    override def op(x: List[Int], y: List[Int]): List[Int] = x ::: y
  }

  /**
   *  1.5) Now try to do the same for List[A]. Is the 'Monoid' typeClass enough for this?
   */
  implicit def genericListMonoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    override def zero: List[A] = List()
    //1.7)
    override def op(x: List[A], y: List[A]): List[A] = x ::: y
  }

  /**
   *  1.6) So... does this mean we can fold or reduce anything that enters in our current Monoid definition? Is the zero method enough?
   *
   *  Tip: try to define the sum function in terms of foldLeft using our current Monoid definition. What are we missing?
   */
  def sum_[A](l: List[A])(implicit m: Monoid[A]): A = ???

  /**
   *  1.7) Complete the Monoid trait with an 'op' function that defines the base operation for its type. You will also have to redefine all your implicit
   *  values to add this operation now.
   */
  //(done above)

  /**
   *  1.8) Try to define the sum function in terms of foldLeft now, using our new Monoid definition.
   */
  def sum[A](l: List[A])(implicit m: Monoid[A]): A = l.foldLeft(m.zero)(m.op)



  /**
   *  2) Consider this RNG implementation. The SimpleRng class accepts a seed which uses to generate a nextInt, and then gives back a new seed for the next int.
   */
  trait RNG {
    type Rand[+A] = RNG => (A, RNG)
    def nextInt: (Int, RNG)
  }

  case class SimpleRNG(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * System.currentTimeMillis() + 0xBL) & 0xFFFFFFFFFFFFL
      val nextRNG = SimpleRNG(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRNG)
    }

    val int: Rand[Int] = _.nextInt

    def boolean: Rand[Boolean] = ???
    def double: Rand[Double] = ???
  }

  /**
   *  2.1) What function would be useful to implement 'boolean' and 'double'?
   */
  /* the 'map' function */

  /**
   *  2.2) Make the RNG trait extend 'Functor' and implement the map method
   */

  /**
   *  2.3) Add a 'unit' function that lets us turn any type A into a Rand[A]
   */

  /**
   *  2.4) Add a 'map2' function that lets us turn any
   */


  /**
   *  3) Define a 'Parser' trait. Which operations would be useful for a parser? To start off define only the function signatures (use ??? as the implementation
   *  if needed)...
   *  Which ones can be implemented without knowing anything about the Parser implementation? In other words, which functions can be implemented just by
   *  using other just defined functions?
   *
   *  Tips:
   *  - we should be able to run a parser and get either the expected output or an exception (run)
   *  - we should be able to turn any value into a parser (unit)
   *  - we should be able to turn any Parser[A] into a Parser[B] (map, flatMap)
   *  - we should be able to merge any two parsers into a new parser (map2)
   *  - we should be able to transform a list of parsers into a parser of list (sequence)
   *  - (optional) we should be able to transform a parser of list into a list of parsers (traverse)
   */
  trait Parser[A] {
    def run(input: String): Result[A]
    def map[B](f: A => B): Parser[B] = this.flatMap(a => Parser.unit(f(a)))
    def flatMap[B](f: A => Parser[B]): Parser[B]
  }
  object Parser {
    def unit[A](a: A): Parser[A] = new Parser[A] {
      override def run(input: String): Result[A] = Success(a,input.length)
      override def flatMap[B](f: A => Parser[B]): Parser[B] = f(a)
    }
    def sequence[A](l: List[Parser[A]]): Parser[List[A]] = l.foldLeft(unit(List(): List[A]))((acc, p) => map2(acc,p)((l, pr) => pr :: l))
    def map2[A,B,C](p1: Parser[A], p2: => Parser[B])(f: (A,B) => C): Parser[C] = p1.flatMap(a => p2.map(b => f(a,b)))
  }

  trait Result[+A]
  case class Success[+A](get: A, charsConsumed: Int) extends Result[A]
  case class Failure(get: Throwable) extends Result[Nothing]

  /**
   *  3.2) Come up with an implementation for your Parser trait.
   *
   *  Once you are done, run the appropriate test to check if your implementation is valid (you will need to change the import on the test file to point to
   *  your Parser implementation.
   *
   *  Note: the names of the methods need to EXACTLY match AT LEAST the ones proposed on the 'tips' section or else the test suite will fail.
   */


  /**
   *  4) Did you notice throughout the course that there was several types of data that shared the 'map' function? List some of them. Con you think of any
   *  commonly used data type that could use the map function and we have not defined yet?
   */
  /*
  - Collections
  - Parsers
  - RNGs
  - IOs
   */

  /**
   *  5) Try to define a typeClass named 'Functor' that encapsulates the 'map' behaviour. Can it be done? If not, why?
   */
  /*
  It can't be done because to implement map we need to reason about the parametric type A inside it's typeClass. For example, for lists we need to reason about
  List[X] and also about the X type inside the list, so we need a tool that goes deeper than just a parametric type A. We need a type constructor!

  e.g.

  trait ListFunctor[A] {
    def map[B](l: List[A])(f: A => B): List[B]
  }

  Notice how we are reasoning about List[A] and also A. So if we try to generalize Functor[A], A here equals List[X], and we need to access the X to implement map!

  trait Functor[A] {
    def map(cls: A)(f: ??? => ???): ???
  }
   */


  def main(args: Array[String]): Unit = {
    println(
      reduceFL(List(1,2,3))(_ + _)
    )
    println(
      reduceFL(List(List(1,2),List(3,4)))(_ ::: _)
    )
    println(
      reduceFL(List(List("Hi","I"),List("am","Mike")))(_ ::: _)
    )
    println(
      reduceFL(List(List('a','b'),List('c','d')))(_ ::: _)
    )

  }
}



