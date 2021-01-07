package solutions

object FunctionExercises {
  /**
   *  0) The msort function below implements a merge-sort on a list given a comparison function lt.
   *  Try to understand its implementation.
   */
  def basic_msort[A](l: List[A])(lt: (A,A) => Boolean): List[A] = {
    val n = l.length / 2
    if (n == 0) l
    else {
      def merge(l1: List[A],l2: List[A]): List[A] = (l1,l2) match {
        case (h1,Nil) => h1
        case (Nil,h2) => h2
        case (h1 :: t1, h2 :: t2) =>
          if (lt(h1,h2)) h1 :: merge(t1,l2)
          else h2 :: merge(t2,l1)
      }
      val (part1, part2) = l.splitAt(n)
      merge(basic_msort(part1)(lt),basic_msort(part2)(lt))
    }
  }

  /**
   * 1) scala.math contains an object called 'Ordering'. This is a parametrized object 'Ordering[A]' that provides an 'lt' function for any parametric [A] type.
   * Inside the 'Ordering' object you will find a value for every simple scala type: Ordering.Int, Ordering.Char, Ordering.String, etc.
   *
   * Rewrite the msort function using the Ordering object from scala.math and its comparison function
   */
  def ord_msort[A](l: List[A])(implicit ord: Ordering[A]): List[A] = {
    val n = l.length / 2
    if (n == 0) l
    else {
      def merge(l1: List[A],l2: List[A]): List[A] = (l1,l2) match {
        case (h1,Nil) => h1
        case (Nil,h2) => h2
        case (h1 :: t1, h2 :: t2) =>
          if (ord.lt(h1,h2)) h1 :: merge(t1,l2)
          else h2 :: merge(t2,l1)
      }
      val (part1, part2) = l.splitAt(n)
      merge(ord_msort(part1),ord_msort(part2))
    }
  }

  /**
   * 2) Sort these lists using the ord_msort method.
   */
  val l1 = List(3,5,1,4,2)
  val l1sorted: List[Int] = ord_msort(l1)(Ordering.Int)

  val l2 = List('a','j','z','d')
  val l2sorted: List[Char] = ord_msort(l2)(Ordering.Char)

  val l3 = List("pineapple","pear","apple","banana")
  val l3sorted: List[String] = ord_msort(l3)(Ordering.String)

  /**
   * 3) Make the ordering parameter implicit. What changes?
   */
  def msort[A](l: List[A])(implicit ord: Ordering[A]): List[A] = {
    val n = l.length / 2
    if (n == 0) l
    else {
      def merge(l1: List[A],l2: List[A]): List[A] = (l1,l2) match {
        case (h1,Nil) => h1
        case (Nil,h2) => h2
        case (h1 :: t1, h2 :: t2) =>
          if (ord.lt(h1,h2)) h1 :: merge(t1,l2)
          else h2 :: merge(t2,l1)
      }
      val (part1, part2) = l.splitAt(n)
      merge(msort(part1),msort(part2))
    }
  }

  /**
   * 4) Sort these lists now using the new msort with implicit ordering
   */
  val l4 = List(3,5,1,4,2)
  val l4sorted: List[Int] = msort(l4)

  val l5 = List('a','j','z','d')
  val l5sorted: List[Char] = msort(l5)

  val l6 = List("pineapple","pear","apple","banana")
  val l6sorted: List[String] = msort(l6)

  /** -------------------- OPTIONAL ------------------------- */

  /**
   * 5) Make a partial function that multiplies its input by 2. The allowed inputs are all positive numbers (including 0).
   */
  val multPos: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = x >= 0

    override def apply(v1: Int): Int = v1 * 2
  }

  /**
   * 6) Make a partial function that multiplies its input by 2. The allowed inputs are all negative numbers.
   */
  val multNeg: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = x < 0

    override def apply(v1: Int): Int = v1 * 2
  }

  /**
   * 7) Combine partial functions from 5) and 6) into a new function defined for all integers. Test it.
   */
  val mult: PartialFunction[Int, Int] = multPos orElse multNeg
}



