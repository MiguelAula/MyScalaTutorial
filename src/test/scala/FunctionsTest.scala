import munit.FunSuite
import exercises.FunctionExercises._

class FunctionsTest extends FunSuite {
  val list = List(5,3,4,2,1)

  test("1) ord_msort") {
    assertEquals(ord_msort(list)(Ordering.Int) , List(1,2,3,4,5))
  }

  test("2) using ord_msort") {
    assertEquals(l1sorted , List(1,2,3,4,5))
    assertEquals(l2sorted , List('a','d','j','z'))
    assertEquals(l3sorted , List("pear","apple","banana","pineapple"))
  }

  test("3) msort (implicit)") {
    assertEquals(ord_msort(list)(Ordering.Int) , List(1,2,3,4,5))
  }

  test("4) using msort") {
    assertEquals(l4sorted , List(1,2,3,4,5))
    assertEquals(l5sorted , List('a','d','j','z'))
    assertEquals(l6sorted , List("pear","apple","banana","pineapple"))
  }

  test("5) multPos") {
    assertEquals(multPos.isDefinedAt(0) , true)
    assertEquals(multPos.isDefinedAt(10) , true)
    assertEquals(multPos.isDefinedAt(-10) , false)
    assertEquals(multPos.apply(10) , 20)
  }

  test("6) multNeg") {
    assertEquals(multPos.isDefinedAt(0) , false)
    assertEquals(multPos.isDefinedAt(10) , false)
    assertEquals(multPos.isDefinedAt(-10) , true)
    assertEquals(multPos.apply(-10) , -20)
  }

  test("7) mult") {
    assertEquals(mult.isDefinedAt(0) , true)
    assertEquals(mult.isDefinedAt(10) , true)
    assertEquals(mult.isDefinedAt(-10) , true)
    assertEquals(mult.apply(-10) , -20)
    assertEquals(mult.apply(10) , 20)
  }
}
