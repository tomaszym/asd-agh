package pl.pej.asd.text

case class RollingHash(value: Int, length: Int, modulo: Int, hashBase: Int) {

  private def mod(n: Int) = RollingHash.mod(n, modulo)

  def roll(substract: Char, add: Char): RollingHash = {

    val out = mod(substract*math.pow(hashBase, length-1).toInt)

    val newValue = mod(mod(mod(value - out)*hashBase + add.toInt))
//    println(s"rolling out $substract (${out}) rolling in $add (${add.toInt}): $newValue")

    RollingHash(newValue, length, modulo, hashBase)
  }
}


object RollingHash {

  private def mod(n: Int, modulo: Int) = ((n%modulo)+modulo)%modulo

  def init(s: String, modulo: Int, hashBase: Int): Int = {
    val xs = for {
      i <- (0 until s.length).toList
    } yield {
//      println(s"   $hashBase^${s.length-i-1} * ${s(i)}")
      mod(math.pow(hashBase, s.length - i - 1).toInt * s(i), modulo)
    }
    mod(xs.sum, modulo)
  }

  def apply(s: String, modulo: Int, hashBase: Int): RollingHash = {
    RollingHash(init(s, modulo, hashBase), s.length, modulo, hashBase)
  }
}