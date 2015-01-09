package mutable

import org.scalatest.FunSuite
import pl.pej.asd.collection.mutable.BitBuffer


class BitBufferSuite extends FunSuite{

  def emptyBitBuffer = BitBuffer.fromSeq(Seq[Boolean]())

  test("Empty BitBuffer is empty and has length of 0") {
    val b = BitBuffer.fromSeq(Seq[Boolean]())

    assert(b.isEmpty)
    assert(b.length == 0)
  }

  test("An element is added, size is 1, element is removed size is 0 x 10") {

    val b = emptyBitBuffer

    for (i <- 0 until 10) {

      assert(b.size === 0)
      b.+=(true)
      assert(b.size === 1)
      assert(b(0) === true)
      b.remove(0)
    }
  }
  test("A lot of elemets is added at the back and the last value is correct") {

    val b = emptyBitBuffer

    val n = 1000000
    b.sizeHint(0,n)
    for (i <- 0 until n) {
      b.+=(true)
    }
    assert(b(n-1) === true)

  }
  test("A lot of elemets is added at the beginning and the first value is correct") {

    val b = emptyBitBuffer

    val n = 100000

    b.sizeHint(0,n)
    for (i <- 0 until n) {
      b.+=:(true)
      assert(b(0) === true)
    }
  }

  test("A lot of elemets is added at the beginning and the end and the first and the last value are correct") {

    val b = emptyBitBuffer

    val n = 10000

    b.sizeHint(0,n)
    for (i <- 0 until n) {

      b.+=:(false)
      b.+=:(true)
      b.+=(false)
      b.+=(true)
      assert(b.head === true)
      assert(b.last === true)
    }
  }

  test("Should throw IndexOutOfBoundsException properly") {
    val b = emptyBitBuffer

    intercept[IndexOutOfBoundsException] {
      b(0)
    }

    b.+=(true)
    b.+=(true)
    b.+=(true)
    intercept[IndexOutOfBoundsException] {
      b(-1)
    }
    assert(b(0) === true)
    assert(b(1) === true)
    assert(b(2) === true)

    intercept[IndexOutOfBoundsException] {
      b(3)
    }

    b.remove(0)
    b.remove(0)
    intercept[IndexOutOfBoundsException] {
      b(-1)
    }
    assert(b(0) === true)

    intercept[IndexOutOfBoundsException] {
      b(1)
    }
  }



}
