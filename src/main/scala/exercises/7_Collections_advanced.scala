package exercises

object CollectionsAdvancedExercises {
  /**
   *  1) Implement the foldLeft function
   */
  def foldLeft[A,B](l: List[A],z: B)(f: (B,A) => B): B = ???

  /**
   *  2) Implement the foldRight function
   */
  def foldRight[A,B](l: List[A],z: B)(f: (A,B) => B): B = ???

  /**
   *  3) Implement reduce in terms of foldLeft for Int
   */
  def reduceIntFL(l: List[Int])(f: (Int,Int) => Int): Int = ???

  /**
   *  4) Implement reduce in terms of foldLeft for String
   */
  def reduceStringFL(l: List[String])(f: (String,String) => String): String = ???

  /**
   *  4) Can you implement reduce in terms of foldLeft for a any parametric type [A]?
   */
  def reduceFL[A](l: List[A])(f: (A,A) => A): A = ???


  /**
   * _____________________________________________________
   *  Extras for practice:
   * _____________________________________________________
   */

  /**
   * Imagine we have a matrix that stores the amount of cm³ of water that hold certain landmarks in Girona.
   */
  val matrix: List[List[Double]] =
    List(
      List(0.5,3,5.2),
      List(1.2,6.2,0.1),
      List(9.2,2.6,3.9)
    )

  /**
   * It just rained 3 cm³ in all of Girona, which means we need a mean to update our matrix. Let's define a function that does this:
   */
  def updateMatrix(matrix: List[List[Double]])(op: Double => Double): List[List[Double]] = matrix.map(_.map(op(_)))


  /**
   * We need to be able to pour all of the water into any slot of the matrix to get it ready for collection
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
}



