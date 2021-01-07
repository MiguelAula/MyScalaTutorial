import exercises.FunctionExercises._
import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}

object PropertyBased extends Properties("OrderedList") {
  /*
  You can do property checks for "all" (100 cases) elements from the given type. e.g: forAll { (l: List[Int]) => ??? }
  That type must be implicitly defined in the forAll library. If it is not, you will have to define a generator for it yourself.
   */

  property("msort (Int)") = forAll { (l: List[Int]) =>
    msort(l) == l.sorted
  }

  property("ord_msort (Char)") = forAll { (l: List[Char]) =>
    msort(l) == l.sorted
  }

  property("ord_msort (String)") = forAll { (l: List[String]) =>
    msort(l) == l.sorted
  }

  /*
  We can also use property testing to check the mult functions we defined "for all" of its inputs
   */
  property("partial mult isDefined") = forAll(Gen.posNum[Int],Gen.negNum[Int]) {
    (posNum,negNum) => {
      multPos.isDefinedAt(posNum) &&
      multNeg.isDefinedAt(negNum) &&
      !multPos.isDefinedAt(negNum) &&
      !multNeg.isDefinedAt(posNum) &&
      multPos.isDefinedAt(0)
    }
  }

  property("total mult isDefined") = forAll { (i: Int) =>
    mult.isDefinedAt(i)
  }

  property("mult calc") = forAll { (i: Int) =>
    mult.apply(i) == i*2
  }

  /*
  (UNDER CONSTRUCTION...)

  So far all the types are already defined in the forAll library. But what if we want to use a parametric List?
  Well that is more complicated...
   */

  /*
  val elemGenerator = Gen.choose(0,2).flatMap {
    case 0 => Gen.alphaStr
    case 1 => Gen.chooseNum(Int.MinValue,Int.MaxValue)
    case 2 => Gen.alphaChar
  }
  val listGen = for {
    numElems <- Gen.choose(5, 12)
    elems <- Gen.listOfN(numElems, elemGenerator)
  } yield elems

  property("msort") = forAll(listGen) { l => {
      def checkSorted[A](list: List[A]): Boolean = {
        msort(list) == list.sorted
      }
      checkSorted(l)
      //msort(l) == l.sorted
    }
  }
   */

}
