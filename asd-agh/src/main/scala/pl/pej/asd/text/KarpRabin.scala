package pl.pej.asd.text

object KarpRabin {

  def findPos(needle: String, haystack: String, modulo: Int = Int.MaxValue/2, hashBase: Int = 7): Option[Int] = {


    val hash = RollingHash(haystack.substring(0, needle.length),modulo, hashBase)
    val needleValue = RollingHash.init(needle, modulo, hashBase)

    val lastPossiblePos = haystack.length - needle.length

    def it(pos: Int, hash: RollingHash): Option[Int] = {

      if(hash.value == needleValue && needle == haystack.substring(pos, pos+needle.length)) Some(pos) else {
        if(pos == lastPossiblePos) None
        else it(pos+1, hash.roll(haystack(pos), haystack(pos+needle.length)))
      }
    }

    it(0, hash)
  }

}
