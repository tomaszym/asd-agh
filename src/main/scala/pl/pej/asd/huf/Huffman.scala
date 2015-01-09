package pl.pej.asd.huf

import scala.collection.mutable


object Huffman {

  abstract class CodeTree extends Ordered[CodeTree] {
    def weight: Int
    def blocks: List[String]

    override def compare(that: CodeTree): Int = this.weight compare that.weight
  }
  case class Fork(left: CodeTree, right: CodeTree, blocks: List[String], weight: Int) extends CodeTree
  case class Leaf(block: String, weight: Int) extends CodeTree {
    def blocks = List(block)
  }


  def encode(text: String, blockSize: Int = 2): List[Int] = {
    if(text.size % 2 == 1) throw new IllegalArgumentException("Obslugujemy tylko parzystodlugie teksty.")

    val chars = text.toList.distinct

    val blocks = for {
      a <- chars
      b <- chars
    } yield new String(Array(a,b))

    val appearances: List[Leaf] = text.grouped(blockSize).toList.groupBy(s => s).map{ case (k,v) =>
      Leaf(k, v.size)
    }.toList

//    // shajnas abunda
//    val code: Map[String, Int] = blocks.zip(blocks.map{ block =>
//      appearances.getOrElse(block, 0)
//    }).toMap


    val queue = new mutable.PriorityQueue[CodeTree]()

    queue.enqueue(appearances:_*)

    def merge(a: CodeTree, b: CodeTree) = Fork(a,b, a.blocks ::: b.blocks, a.weight+b.weight)

    while(queue.size > 1) {

      val a = queue.dequeue()
      val b = queue.dequeue()

      val c = merge(a,b)

      queue.enqueue(c)
    }

    def encode(tree: CodeTree)(text: List[String]): List[Int] = {
      def lookup(tree: CodeTree)(c: String): List[Int] = tree match {
        case Leaf(_, _) => List()
        case Fork(left, right, _, _) if left.blocks.contains(c) => 0 :: lookup(left)(c)
        case Fork(left, right, _, _) => 1 :: lookup(right)(c)
      }
      text flatMap lookup(tree)
    }

    val tree = queue.dequeue()

    encode(tree)(text.grouped(2).toList)


  }




}
