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

}



