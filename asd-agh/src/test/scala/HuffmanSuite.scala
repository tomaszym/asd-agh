import org.scalatest.FunSuite
import pl.pej.asd.huf.{FrequencyCount, Huffman}
import rapture.io._
import rapture.codec._
import encodings.`UTF-8`


class HuffmanSuite extends FunSuite {


  def testCoding(testPhrase: String, groupSizeFrom: Int, groupSizeTo: Int): Unit = {

    for(groupSize <- groupSizeFrom to groupSizeTo){
      
      val testPhraseFit = testPhrase.take(testPhrase.size/groupSize*groupSize)

      val codeTree = Huffman.buildCodeTree(FrequencyCount(StringIsInput(testPhraseFit),groupSize))

      val encoded = Huffman.encode(codeTree, StringIsInput(testPhraseFit), groupSize)

      val clone = encoded.clone()

      val res = StringBuilder.newBuilder
      Huffman.decode(codeTree,encoded,res)

      assert(res.result === testPhraseFit)
    }
  }

  val simpleTestPhrase = "aaabbbcce"
  test(s"Reversible for $simpleTestPhrase") {
    testCoding(simpleTestPhrase, 1, 5)
  }

  test(s"Reversible for ${szalaiEva.take(10)}...") {
    testCoding(szalaiEva,1,5)
  }

  lazy val szalaiEva = """Szabad a csók ma szabad a szerelem
                    |Szabadnapos a szívem, gyere velem!
                    |Szalai Éva te vagy a mindenem
                    |Én Gumicukor vagyok a tenyereden
                    |
                    |Úgy érzem én, veled a végén vagy a legelején
                    |Szerető nő vagy, annyira jó
                    |Berepülsz a radar alá, te lopakodó
                    |
                    |És hányszor segítettél már
                    |És hányszor hagytál cserben
                    |És hányszor kerültem már föléd az ágyba’ nyakkendőbe’
                    |
                    |Szabad péntek, szabad szombat
                    |Szabad szerelmeskedni
                    |Szabad Szalai Évával az édenkertbe’ almát szedni
                    |
                    |Igen, igen
                    |Igen, igen
                    |Igen, igen
                    |
                    |Szabad a csók ma szabad a szerelem
                    |Szabadnapos a szívem, gyere velem!
                    |Szalai Éva te vagy a mindenem
                    |Gumicukor vagyok a tenyereden
                    |
                    |Úgy érzem én, veled a végén vagy a legelején
                    |Szerető vagy és annyira jó
                    |Berepülsz a radar alá, te lopakodó
                    |
                    |Hányszor segítettél már
                    |És hányszor hagytál cserben
                    |Hányszor kerültem már föléd az ágyba nyakkendőbe
                    |
                    |Szabad péntek, szabad szombat
                    |Szabad szerelmeskedni
                    |Szabad Szalai Évával az édenkertben almát szedni
                    |
                    |Hányszor segítettél már
                    |És hányszor hagytál cserben
                    |Hányszor kerültem már föléd az ágyba’ nyakkendőbe’
                    |
                    |Szabad péntek, szabad szombat
                    |Szabad szerelmeskedni
                    |Szabad-e a Szalai Évával az édenkertben almát szedni
                    |az édenkertben almát szedni
                    |az édenkertben almát szedni
                    |az édenkertben szedni az almát.""".stripMargin

}
