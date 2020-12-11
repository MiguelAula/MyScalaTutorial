package exercises

object CollectionsExercises {
  /**
   *  1) Implement your own list (...)
   */
  sealed trait MyList[+A]
  case object Nil extends MyList[Nothing]
  case class Cons[+A](head: A, tail: MyList[A]) extends MyList[A]

  object MyList{
    def apply[A](a:A*): MyList[A] = if(a.isEmpty) Nil else Cons(a.head, apply(a.tail: _*))
  }
}



