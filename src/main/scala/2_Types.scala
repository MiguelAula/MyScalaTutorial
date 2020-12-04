object BasicTypes {
  val integer: Int = 1
  val char: Char = 'a'
  val boolean: Boolean = true
  val string: String = "myString"
  val double: Double = 1.5e3
  val float: Float = 1.5e3.toFloat
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
 *  This is a quick "Option" / "Either" introduction. We will see more use cases later on...
 */
object OptionEither {
  val someInt: Option[Int] = Some(5)
  val noInt: Option[Int] = None

  val right: Either[Throwable,Int] = Right(5)
  val left: Either[Throwable,Int] = Left(new Throwable("No integer found"))
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
 *  You can parametrize on the type of a function
 */
object TypeParametrization {
  val function1: Int => Int = (i: Int) => i+1
  val function2: Int => Int = _ + 1

  /* DID YOU KNOW...
  Scala has type inference. Which means you can omit the type either on the value definition or in the function declaration.
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