package pl.pej.asd.collection.mutable

import java.nio.ByteBuffer

import scala.collection.generic.{GenericTraversableTemplate, Growable, CanBuildFrom}
import scala.collection.{CustomParallelizable, mutable}
import scala.collection.mutable.{ArrayBuffer, Buffer, BufferLike, Builder}

final class BitBuffer private (private var data: Array[Int], var length: Int) extends
                                            mutable.AbstractSeq[Boolean] with
                                            mutable.Buffer[Boolean] with
                                            mutable.BufferLike[Boolean, BitBuffer] with
//                                            mutable.IndexedSeq[Boolean] with
                                            mutable.IndexedSeqOptimized[Boolean, BitBuffer]
//                                            mutable.ResizableArray[Int]
                                              {

  override protected[this] def newBuilder: Builder[Boolean, BitBuffer] =
    BitBuffer.newBuilder

  import BitBuffer._


  var dataSize = data.size
  override def seq = ???

  /** The first bit of the array which is contained in the coll
    */
  private var first = -1

  /** Translates the index of an element in the collection
    * to index of an integer in `data` array, which is called
    * chunk from here.
    *
    * @param idx index of an element in the collection
    * @return index of data chunk
    */
  private def chunkIdx(idx: Int): Int = {
    if(idx < 0 || idx >= length) throw new IndexOutOfBoundsException()
    (first + idx) / bits
  }

  /** Helper function, when `data` reference is not needed.
    *
    * @param idx
    * @return
    */
  private def chunk(idx: Int): Int = data(chunkIdx(idx))

  /** Creates Int with the mask on the bit
    *
    * @param idx
    * @return
    */
  private def mask(idx: Int): Int = 1 << ((first + idx) % bits)

  override def apply(idx: Int): Boolean = {
    val m = mask(idx)
    (data(chunkIdx(idx)) & m) == m
  }

  /** not exactly size hint, but who cares about naming conventions after 2 am
    *
    * @param before
    * @param after
    */
  def sizeHint(before: Int, after: Int): Unit = {
    moreSpace(before/bits, after/bits)
  }


  private def moreSpace(before: Int, after: Int): Unit = {

    val newData = Array.fill(before + after + dataSize)(0)

    scala.compat.Platform.arraycopy(data, 0, newData, before, dataSize)
    data = newData
    dataSize = data.size
    first = first + before*bits
  }


  override def update(idx: Int, elem: Boolean): Unit = {

    if(elem) {
      data(chunkIdx(idx)) = chunk(idx) | mask(idx)
    } else {
      data(chunkIdx(idx)) = chunk(idx) & ~mask(idx)
    }
  }

  override def +=(elem: Boolean): this.type = {

    if((first+length)/bits < dataSize) {
      length = length+1
      update(length-1, elem)
    } else {
      moreSpace(0, 2)
      +=(elem)
    }
    this
  }

  override def clear(): Unit = ???

  override def remove(n: Int): Boolean = {
    val prev = apply(n)

    if(n == 0) {
      first = first + 1
      length = length - 1
    } else if(n == length-1) {
      length = length -1
    } else {
      ??? // :-)
    }

    prev
  }

  override def +=:(elem: Boolean): this.type = {

    if(first > 0) {
      if(length != 0)
        first = first - 1
      length = length + 1
      update(0, elem)
    } else {
      moreSpace(2, 0)
      +=:(elem)
    }
    this
  }
  override def insertAll(n: Int, elems: Traversable[Boolean]): Unit = elems.foreach{ e => +=(e)}

  override def toString(): String = "BitBuffer" + iterator.mkString("(", ",", ")")

  def dataAsByteArray: Array[Byte] = {


    val buffer = ByteBuffer.allocate(data.size*4)

    data.map { i =>
      buffer.putInt(i)
    }
    buffer.array()
  }
}


object BitBuffer {

  private val bits = 32

  def fromSeq(buf: Seq[Boolean]): BitBuffer = {
    val groups = new Array[Int](math.ceil(buf.length.toDouble / bits).toInt)

    implicit def bool2int(b:Boolean): Int = if (b) 1 else 0

    for (i <- 0 until buf.length)
      groups(i / bits) |= buf(i) << (i % bits)
    new BitBuffer(groups, buf.length)
  }
  def apply(bases: Boolean*) = fromSeq(bases)
  def newBuilder: Builder[Boolean, BitBuffer] =
    new ArrayBuffer mapResult fromSeq

  implicit def canBuildFrom: CanBuildFrom[BitBuffer, Boolean, BitBuffer] =
    new CanBuildFrom[BitBuffer, Boolean, BitBuffer] {
      def apply(): Builder[Boolean, BitBuffer] = newBuilder
      def apply(from: BitBuffer): Builder[Boolean, BitBuffer] = newBuilder
    }
}
