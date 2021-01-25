package solutions

import scala.annotation.tailrec

object CollectionsAdvancedExercises {
  /**
   *  1) Implement the foldLeft function
   */
  @tailrec
  def foldLeft[A,B](l: List[A], z: B)(f: (B,A) => B): B = l match {
    case h :: t => foldLeft(t,f(z,h))(f)
    case Nil => z
  }

  /*
  How does this behave? Example breakdown:

    foldLeft(List('A','B','C'),'z')(_ + _)

    1) case 'A' :: List('B','C') => FL(List('B','C'), 'z' + 'A')
    2) case 'B' :: List('C') => FL(List('C'), 'zA' + 'B')
    3) case 'C' :: Nil => FL(Nil, 'zAB' + 'C')
    4) case Nil => 'zABC'
   */

  /**
   *  2) Implement the foldRight function
   */
  def foldRight[A,B](l: List[A],z: B)(f: (A,B) => B): B = l match {
    case h :: t => f(h,foldRight(t,z)(f))
    case Nil => z
  }

  /*
  How does this behave? Example breakdown:

  (Note: the =/=> notation means it continues AFTER the recursive call has returned, which means you have to keep reading the following lines before continuing)

    foldRight(List('A','B','C'),'z')(_ + _)

    1) case 'A' :: List('B','C') => 'A' + FR(List('B','C'),'z') =/=> 'A' + 'BCz' => 'ABCz'
    2) case 'B' :: List('C') => 'B' + FR(List('C'),'z') =/=> 'B' + 'Cz' => 'BCz'
    3) case 'C' :: Nil => 'C' + FR(Nil,'z') =/=> 'C' + 'z' => 'Cz'
    4) case Nil => 'z'
   */

  /**
   * Notice how the foldLeft function is tail recursive by definition, while the foldRight function is not. Nevertheless, the FR function can also be made
   * tailrec using a method called trampolining which we will not explain in this course.
   */

  /**
   *  3) Implement reduce in terms of foldLeft for Int
   */
  def reduceIntFL(l: List[Int])(f: (Int,Int) => Int): Int = l.foldLeft(0)(f)

  /**
   *  4) Implement reduce in terms of foldLeft for String
   */
  def reduceStrFL(l: List[String])(f: (String,String) => String): String = l.foldLeft("")(f)

  /**
   *  5) Can you implement reduce in terms of foldLeft for a parametric type [A]? If not, why? What extra information would you need?
   */
  def reduceFL[A](l: List[A])(f: (A,A) => A): A = l.foldLeft(???)(f)
  /* It cannot be done! We need to know which would be the initial value for the type A (a zero value) */


  /**
   * _____________________________________________________
   *  Optional extras:
   * _____________________________________________________
   */

  /**
   *  6) Imagine we have a matrix that stores the amount of cm³ of water that hold certain landmarks in Girona.
   */
  val matrix: List[List[Double]] =
    List(
      List(0.5,3,5.2),
      List(1.2,6.2,0.1),
      List(9.2,2.6,3.9)
    )

  /**
   *  7) It just rained 3 cm³ in all of Girona, which means we need a mean to update our matrix. Let's define a function that does this:
   */
  def updateMatrix(matrix: List[List[Double]])(op: Double => Double): List[List[Double]] = matrix.map(_.map(op(_)))


  /**
   *  8) We need to be able to pour all of the water into any slot of the matrix to get it ready for collection
   */
  def pourTo(matrix: List[List[Double]], slot: (Int,Int)): List[List[Double]] = {
    val acc = matrix.foldLeft(0.0)((n,l) => l.sum + n)
    matrix.zipWithIndex.map { case (row, i) =>
      row.zipWithIndex.map { case (_, j) =>
        if (slot != (i, j)) 0
        else acc
      }
    }
  }

  /**
   * 9) Create the file test/scala/Collections_advanced_test, and make a simple test case for this two functions. Would it be useful to use property based testing in this case?
   * Give it a try!
   */

  def main(args: Array[String]): Unit = {
    println(
      "FL (self): " + foldLeft(List(1,2,3),"s")(_ + _)
    )
    println(
      "FL (real): " + List(1,2,3).foldLeft("s")(_ + _)
    )

    println(
      "FR (self): " + foldRight(List(1,2,3),"s")(_ + _)
    )
    println(
      "FR (real): " + List(1,2,3).foldRight("s")(_ + _)
    )

    /* ----------------------------- */

    println(
      updateMatrix(matrix)(_ + 1)
    )

    println(
      pourTo(matrix,(0,1))
    )
  }

}



