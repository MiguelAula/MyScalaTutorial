package theory

/**
 *  Scala basic types (subtype of AnyVal)
 */
object BasicTypes {
  val integer: Int = 1
  val short: Short = 1
  val long: Long = 1
  val byte: Byte = 1
  val char: Char = 'a'
  val boolean: Boolean = true
  val string: String = "myString"
  val double: Double = 1.5e3
  val float: Float = 1.5e3.toFloat

  /** unit has exactly one instance and is a type that has no meaningful value */
  val unit: Unit = ()
}

object Tuples {
  /** declaring tuples */
  val pair: (Int,Char) = (5,'z')
  /* ... */
  val tuple5: (Int,Char,Boolean,String,Double,Float) = (1,'a',true,"abc",1.5,3)
  /* ... */
  //val tuple22 ... is the maximum tuple size

  /** accessing tuple value */
  val tuple5_string: String = tuple5._4
  val tuple5_double: Double = tuple5 match {
    case (i, c, bool, str, d, fl) => d
  }
}

/**
 *
 */
object Classes {
  class MyClass(param1: Int, param2: Boolean)

  val a: MyClass = new MyClass(1,true)
}

/**
 *  This is a quick "Option" / "Either" introduction. We will see more use cases later on...
 */
object OptionEither {
  val someInt: Option[Int] = Some(5)
  val noInt: Option[Int] = None

  val right: Either[Throwable,Int] = Right(5)
  val left: Either[Throwable,Int] = Left(new Throwable("No integer found"))
}

/**
 *  A second insight on patternmatching: how to add program logic
 */
object OptionEitherPatternMatching {
  import org.scalacheck.Gen
  import org.scalacheck.Gen.Choose.chooseInt

  val optionGenerator: Gen[Option[Int]] = Gen.option(Gen.posNum[Int])
  val eitherGenerator: Gen[Either[Throwable,Int]] = Gen.either(Gen.const(new Throwable("NO INT FOUND!")),Gen.posNum[Int])

  val randOption: Option[Int] = optionGenerator.sample.get
  val randEither: Either[Throwable,Int] = eitherGenerator.sample.get

  /**
   * We can use pattern-matching to decide over an option...
   */
  val strOption = randOption match {
    case Some(value) => s"This option contains the value: $value"
    case None => "This option does not contain any value"
  }

  val strEither = randEither match {
    case Left(ex) => s"This either contains the exception: ${ex.getMessage}"
    case Right(value) => s"This either contains the value: $value"
  }

  def main(args: Array[String]): Unit = {
    println(randOption)
    println(randEither)

    println(strOption)
    println(strEither)
  }
}

object Function {
  val function1: Int => Int = (i: Int) => i+1
  val function2: Int => Int = _ + 1

  /* DID YOU KNOW...
  Scala has type inference. Which means you can omit the type either on the value definition or in the function declaration. Just one of the two is enough!
  Also you can use _ to represent an anonymous parameter which will only be used once in the function implementation.
  */

  val countChars: String => Int = (x: String) => x.length
  val sumAllIfCond: (Int,Int,Int,Boolean) => Option[Int] = (a, b, c, doSum) => if (doSum) Some(a+b+c) else None

  def main(args: Array[String]): Unit = {
    println(s"countChars function: $countChars")
    println(s"countChars: ${countChars("12345")}")
    println(s"sum3ifTrue: ${sumAllIfCond(5,10,15,countChars("four") == 4)}")
    println(s"sum3ifTrue: ${sumAllIfCond(5,10,15,countChars("four") == 5)}")
  }
}

/**
 *  We have seen the main types Scala has to offer. Let's now see how they're related to one another.
 */
object TypeHierarchy {
  /* https://docs.scala-lang.org/tour/unified-types.html */
  val any1: Any = true
  val any2: Any = List(3,5,6)
  val any3: Any = (x: Int) => x^2
  val any4: Any = Some(1)

  val nothing: Nothing = ???  //there is no actual value that has type Nothing. It is used to signal abnormal termination (e.g. thrown exception)
  val nullVal: Null = null    //as with Unit, null has exactly one instance and exists only for JVM interoperability. It's not really used in Scala coding.
}

/**
 *  Scala allows you to declare your own types
 */
object TypeDeclaration {
  type Counter = Int
  type Output = String

  def printA(counter: Counter): Output = (1 to counter).map(_ => 'A').foldLeft("")(_ + _)

  def main(args: Array[String]): Unit = {
    println(printA(10))
  }
}

/**
 *  You can parametrize the type of a function so it can be used for any given type.
 */
object TypeParametrization {
  /**
   * A parametrized parser
   */
  def parse[A](input: A): String = input match {
    case x: String => s"Here's your string: $x"
    case x: Int => s"Here's your integer: $x"
    case x: Double => s"Here's your double: $x"
    case x: Float => s"Here's your float: $x"
    case x => s"Here's your unknown type: $x"
  }

  /* DID YOU KNOW...
  In Scala all data types are created with a default ".toString" method. Even if you just defined them just now!
  Also, when you concatenate a value to a string, Scala automatically calls the .toString function on the value for you.
   */

  /**
   * Here we present a first insight on the signature of the ever-present functions in FP: map and flatMap
   */
  def map[A,B](list: List[A])(f: A => B): List[B] = ???
  def flatten[A](list: List[List[A]]): List[A] = ???
  def flatMap[A,B](list: List[A])(f: A => List[B]): List[B] = flatten(map(list)(f))

  def main(args: Array[String]): Unit = {
    println(parse(List(3)))
  }
}

/**
 *  You can also make type constructors... this can be a bit confusing so don't worry if you don't get it yet! It will come up again later on in
 *  a more clear example.
 */
object TypeConstructor {
  /* https://www.atlassian.com/blog/archives/scala-types-of-a-higher-kind */

  trait WithMap[F[_]] {
    def map[A,B](fa: F[A])(f: A => B): F[B]
  }

}