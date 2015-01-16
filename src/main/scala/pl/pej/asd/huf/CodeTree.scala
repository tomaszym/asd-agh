package pl.pej.asd.huf

import scala.collection.mutable

sealed abstract class CodeTree extends Ordered[CodeTree] {
  def weight: Int
  def blocks: List[String]

  override def compare(that: CodeTree): Int = that.weight compare this.weight
}

case class Fork(left: CodeTree, right: CodeTree, blocks: List[String], weight: Int = 0) extends CodeTree {
  override def toString: String = s"($weight:$left:$right)"
}

case class Leaf(block: String, weight: Int = 0) extends CodeTree {
  def blocks = List(block)

  override def toString: String = s"<$weight:$block>"
}

object CodeTree {


  def toMap(tree: CodeTree): Map[String, String]= {



    ???
  }

  def fromMap(treeText: Map[String,String]): CodeTree = {

    ???
  }


}