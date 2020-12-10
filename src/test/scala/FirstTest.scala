import munit.FunSuite

class FirstTest extends FunSuite {
  test("pass") {
    val obtained = 43
    val expected = 43
    assertEquals(obtained, expected)
  }

  test("failure") {
    val obtained = 42
    val expected = 43
    assertEquals(obtained, expected)
  }
}
