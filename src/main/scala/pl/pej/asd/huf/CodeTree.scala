package pl.pej.asd.huf

import scala.collection.mutable

abstract class CodeTree extends Ordered[CodeTree] {
  def weight: Int
  def blocks: List[String]

  override def compare(that: CodeTree): Int = that.weight compare this.weight
}

case class Fork(left: CodeTree, right: CodeTree, blocks: List[String], weight: Int) extends CodeTree {
  override def toString: String = s"($weight:$left:$right)"
}

case class Leaf(block: String, weight: Int) extends CodeTree {
  def blocks = List(block)

  override def toString: String = s"<$weight:$block>"
}
