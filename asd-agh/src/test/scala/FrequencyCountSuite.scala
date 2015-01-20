import org.scalatest.FunSuite
import pl.pej.asd.huf.FrequencyCount

class FrequencyCountSuite extends FunSuite {

  test("Three element code tree") {

    val real = FrequencyCount(Map(
      "a" -> 1,
      "b" -> 2,
      "c" -> 3,
      "d" -> 4
    ),2)

    val encoded = real.encode
    val decoded = FrequencyCount.decode(encoded)

    assert(real.freq.toSet === decoded.freq.toSet)
    assert(real.groupSize === decoded.groupSize)
  }

}
