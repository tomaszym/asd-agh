package pl.pej.asd.graph.util


trait FullBitSetUtil {


  def fullSetBitMask(size: Int) = {

    Array.fill(math.ceil(size.toDouble / 62).toInt)(Long.MaxValue)

  }


}
