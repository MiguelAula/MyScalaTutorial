package solutions

object TypeConstructorExercises {
  /**
   *  1) In the previous chapter we tried to implement 'Functor' as a typeClass but we noticed that using a typeClass was not enough. We needed 'Functor' to
   *  reason about the type inside a typeClass. This is exactly what a type constructor lets us do!
   *
   *  Try to define the 'Functor' trait again, but this time as a type constructor.
   *
   *  Remember: the 'Functor' trait is a generalization of all types that can hold the 'map' function.
   */
  trait Functor[F[_]] {
    def map[A,B](fa: F[A])(f: A => B): F[B]
  }

  /**
   *  2) An other common generalization is called 'Applicative'. This trait is defined by the operators 'unit' and 'map2'.
   *
   *  Define the 'Applicative' trait.
   */
  trait Applicative[F[_]] {
    def unit[A](a: => A): F[A]
    def map2[A,B,C](fa: F[A], fb: F[B])(f: (A,B) => C): F[C] = apply(apply(unit(f.curried))(fa))(fb)
    def apply[A,B](fab: F[A => B])(fa: F[A]): F[B] = map2(fab,fa)(_(_))

    def map[A,B](fa: F[A])(f: A => B): F[B] = apply(unit(f))(fa)
    def mapMap2[A,B](fa: F[A])(f: A => B): F[B] = map2(fa,fa)((a,_) => f(a))
  }

  /** 2.1) This trait is called applicative because an other set of primitive operators to define it is 'unit' and 'apply'.
   *
   *  Note: the primitive operators are the minimum set of operators that allow a trait to be fully implemented.
   *
   *  Also notice that the 'map' function can be implemented using the applicative primitives. This means all applicatives are also functors! Use inheritance
   *  to reflect this fact in your code.
   */

  /** 2.2) (HARD) Define the function 'apply' for the applicative trait. Here is its signature:
   *    def apply[A,B](fab: F[A => B])(fa: F[A]): F[B]
   *
   *  Implement apply in terms of unit and map2.
   *  Implement map2 in terms of unit and apply.
   *  Implement map in terms of either unit and map2, or unit and apply, your choice.
   *
   *  Notice that no matter which set of primitives we decide, we will always need to implement two functions to define an applicative:
   *    'unit' and ('map2' or 'apply')
   */

  /**
   *  3) There is also a generalization for types that can be folded. Define a 'Foldable' trait with the
   */
  /*
  trait Foldable[F[_]] {
    def foldRight[A,B](as: F[A])(z: B)(f: (A, B) => B): B
    def foldLeft[A,B](as: F[A])(z: B)(f: (B, A) => B): B
    def foldMap[A,B](as: F[A])(f: A => B)(mb: Monoid[B]): B
    def concatenate[A](as: F[A])(m: Monoid[A]): A =
      foldLeft(as)(m.zero)(m.op)
  }
  */









  /**
   *  Consider this JSON definition...
   */
  trait JSON
  case object JNull extends JSON
  case class JNumber(get: Double) extends JSON
  case class JString(get: String) extends JSON
  case class JBool(get: Boolean) extends JSON
  case class JArray(get: IndexedSeq[JSON]) extends JSON
  case class JObject(get: Map[String, JSON]) extends JSON

}



