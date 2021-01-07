package exercises

object TypeClassExercises {
  /**
   *  1) In the previous chapter we noticed that we needed to know the initial value for a type to be able to implement reduce in terms of foldLeft for
   *  any type [A]. We needed a zero value.
   *
   *  Define a typeClass for [A] called 'Monoid'. It should contain a single method called 'zero'.
   */

  /**
   * 1.2) Define at least two implicit values for the Monoid typeClass (Int,String)
   */

  /**
   *  1.3) Now use this typeClass to implement reduceFL (you can change the given signature)
   */

  /**
   *  1.4) The 'Monoid' typeClass allows you to implement reduceFL for any simple data type. But what if A is by itself a typeClass like List[A]?
   *  Try to define an implicit value for the 'Monoid' typeClass for List[Int].
   */

  /**
   *  1.5) Now try to do the same for List[A]. Is the 'Monoid' typeClass enough for this?
   */

  /**
   *  1.6) So... does this mean we can fold or reduce anything that enters in our current Monoid definition? Is the zero method enough?
   *
   *  Tip: try to define the sum function in terms of foldLeft using our current Monoid definition. What are we missing?
   */

  /**
   *  1.7) Complete the Monoid trait with an 'op' function that defines the base operation for its type. You will also have to redefine all your implicit
   *  values to add this operation now.
   */

  /**
   *  1.8) Try to define the sum function in terms of foldLeft now, using our new Monoid definition.
   */



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


  /**
   *  5) Try to define a typeClass named 'Functor' that encapsulates the 'map' behaviour. Can it be done? If not, why?
   */

}



