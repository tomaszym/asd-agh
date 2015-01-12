package pl.pej.asd.huf

import pl.pej.asd.collection.mutable.BitBuffer
import rapture.io.Input

import scala.collection.mutable


object Huffman {


  def buildCodeTree(frequencyCount: FrequencyCount): CodeTree = {

    val queue = new mutable.PriorityQueue[CodeTree]()

    val leaves = frequencyCount.freq.foreach{ case (str, count) =>
      queue.enqueue(Leaf(str, count))
    }





    def merge(a: CodeTree, b: CodeTree) = Fork(a, b, a.blocks ::: b.blocks, a.weight + b.weight)

    while (queue.size > 1) {

      val a = queue.dequeue()
      val b = queue.dequeue()

      val c = merge(a, b)

      queue.enqueue(c)
    }


    //

    queue.dequeue()
  }

  def encode(codeTree: CodeTree, in: Input[Char], groupSize: Int = 2): Array[Byte] = {

    val bitBuffer = BitBuffer.fromSeq(Seq[Boolean]())
    bitBuffer.sizeHint(0,8)

    def encodeString(tree: CodeTree)(c: String): List[Int] = tree match {

      case Leaf(_, _) => List()
      case Fork(left, right, _, _) if left.blocks.contains(c) =>
        bitBuffer.+=(false)
        encodeString(left)(c)
      case Fork(left, right, _, _) =>
        bitBuffer.+=(true)
        encodeString(right)(c)
    }


    @scala.annotation.tailrec
    def readN(n: Int, acc: List[Char]): Unit = {
      acc match {
        case list: List[Char] if list.size == n =>

          encodeString(codeTree)(list.mkString)

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

    bitBuffer.dataAsByteArray
  }
}