package pl.pej.asd.huf

import pl.pej.asd.collection.mutable.BitBuffer
import rapture.fs.FileUrl
import rapture.io.{Output, Writer, Input}

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Try


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

    queue.dequeue()
  }

  var kkk = 30

  def decode(codeTree: CodeTree, in: BitBuffer, out: mutable.StringBuilder): Unit = {

    def f(t: CodeTree): Option[String] = {
        t match {
          case Fork(l, r, _, _) =>
            in.headOption match {
              case Some(false) =>
                in.remove(0)
                f(l)
              case Some(true) =>
                in.remove(0)
                f(r)
              case None =>
                in.remove(0)
                None //throw new IllegalArgumentException("Incomplete bit sequence")
            }
          case Leaf(c, _) => Some(c)
        }
    }
    f(codeTree) match {
      case Some(chunk) =>
        out.++=(chunk)
        if(in.nonEmpty)
          decode(codeTree, in, out)
      case None =>
    }


  }
  var i = 40

  def encode(codeTree: CodeTree, in: Input[Char], groupSize: Int = 2): BitBuffer = {

    val bitBuffer = BitBuffer.fromSeq(Seq[Boolean]())
    bitBuffer.sizeHint(0,1024)
    def encodeString(tree: CodeTree)(c: String): List[Int] = tree match {

      case Leaf(_, _) => List()
      case Fork(left, right, _, _) if left.blocks.contains(c) =>
        bitBuffer.+=(false)
        encodeString(left)(c)
      case Fork(left, right, _, _) if right.blocks.contains(c)=>
        bitBuffer.+=(true)
        encodeString(right)(c)
      case _: Fork => throw new IllegalStateException("There's no code for a chunk: " + c)
    }


    @scala.annotation.tailrec
    def readN(n: Int, acc: List[Char]): Unit = {
      acc match {
        case list: List[Char] if list.size == n =>

          val str = list.reverse.mkString
          encodeString(codeTree)(str)

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

    bitBuffer
  }
}