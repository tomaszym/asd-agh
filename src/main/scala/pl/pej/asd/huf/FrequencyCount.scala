package pl.pej.asd.huf

import java.io.{BufferedReader, BufferedWriter}

import rapture.io._

case class FrequencyCount(freq: Map[String, Int], groupSize: Int) {

  def encode: String = {

    groupSize.toString + "०" +
    freq.map { case (chunk, count) =>
      s"$chunk×$count"
    }.mkString("०")
  }
}

object FrequencyCount {
  def apply(in: Input[Char], groupSize: Int): FrequencyCount = {

    assert(groupSize > 0)
//    val n = 1024
//    val tmp = new Array[Byte](n)
//    val bin = new BufferedInputStream(in,n)
//
//    var read: Int = 0
//    var rest = ""
//
//    while(read != -1) {
//      read = bin.read(tmp,0,n)
//
//      val s = new String()

    val counts = scala.collection.mutable.Map[String, Int]()

    @scala.annotation.tailrec
    def readN(n: Int, acc: List[Char]): Unit = {
      acc match {
        case list: List[Char] if list.size == n =>

          val str = list.mkString.reverse
          counts.update(str, counts.getOrElse(str,0)+1)

          in.read() match {
            case None =>

            case Some(c) =>
              readN(n, c::Nil)
          }
        case _: List[Char] =>
          in.read() match {
            case None =>

            case Some(c) => readN(n, c::acc)
          }
      }
    }

    readN(groupSize, Nil)

    FrequencyCount(counts.toMap, groupSize)
  }

  def decode(encoded: String): FrequencyCount = {

    val (groupSize, frequencies) = encoded.split("०").toList match {
      case head::tail =>
        val freq = tail.map(_.split("×").toList).map{ line =>
          (line(0), Integer.parseInt(line(1)))
        }.toMap

        (Integer.parseInt(head), freq)
    }

    FrequencyCount(frequencies, groupSize)
  }
}
