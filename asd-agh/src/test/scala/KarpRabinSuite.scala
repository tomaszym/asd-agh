import org.scalatest.FunSuite
import pl.pej.asd.text.KarpRabin

class KarpRabinSuite extends FunSuite {

  test("Toy test abcd") {
    assert(KarpRabin.findPos("bcd","abcdefghijk") === Some(1))
  }


  test("Finds pattern in the begining") {
    assert(KarpRabin.findPos("kismadár", szalljHaystack) === Some(10))
  }
  test("Finds pattern in the middle") {
    assert(KarpRabin.findPos("Csak", szalljHaystack) === Some(166))
  }
  test("Finds pattern in the end") {
    assert(KarpRabin.findPos("türürü", szalljHaystack) === Some(1548))
  }

  val szalljHaystack = """Szállj el kismadár,
                   |Nézd meg, hogy merre jár,
                   |Mondd el, hogy merre járhat Ő!
                   |
                   |Mondd el, hogy szeretem,
                   |Mondd el, hogy kell nekem,
                   |Mondd el, hogy semmi más nem kell!
                   |
                   |Csak a hold az égen,
                   |Csak a nap ragyogjon,
                   |Simogasson a szél,
                   |Simogasson, ha arcomhoz ér.
                   |Csak a hold ragyogjon,
                   |Csak a nap az égen,
                   |Nekem semmi más nem kell!
                   |
                   |Kell, hogy rátalálj,
                   |Szállj el kismadár,
                   |Nézd meg, hogy merre járhat Ő!
                   |
                   |Vidd el a levelem,
                   |Mondd el, hogy kell nekem,
                   |Mondd el, hogy semmi más nem kell!
                   |
                   |Csak a hold az égen,
                   |Csak a nap ragyogjon,
                   |Simogasson a szél,
                   |Simogasson, ha arcomhoz ér.
                   |Csak a hold ragyogjon,
                   |Csak a nap az égen,
                   |Nekem semmi más nem...
                   |
                   |Soha ne gyere, ha most nem jössz!
                   |Soha ne szeress, ha most nem vagy itt! Huóóó...
                   |Soha ne gyere, ha most nem jössz!
                   |Soha ne szeress, ha most nem vagy itt! Huóóú...
                   |
                   |Váp, váp, váppsuvápp,
                   |Váp, váp, suváppápp,
                   |Váp, váp, váppsuváppáppéj.
                   |Váp, váp, suváppá,
                   |Váp, váp, vápsuvápp,
                   |Váp, váp, váppsuváppáppéj.
                   |
                   |Csak a hold az égen,
                   |Csak a nap ragyogjon,
                   |Simogasson a szél,
                   |Simogasson, ha arcomhoz ér.
                   |Csak a hold ragyogjon,
                   |Csak a nap az égen,
                   |Nekem semmi más nem...
                   |
                   |Váp, váp, váppsuvápp,
                   |Váp, váp, suváppápp,
                   |Váp, váp, váppsuváppáppéj.
                   |Váp, váp, suváppá,
                   |Váp, váp, vápsuvápp,
                   |Váp, váp, váppsuváppáppéj.
                   |
                   |Csak a hold az égen,
                   |Csak a nap ragyogjon,
                   |Simogasson a szél,
                   |Simogasson, ha arcomhoz ér.
                   |Csak a hold ragyogjon,
                   |Csak a nap az égen,
                   |Nekem semmi más nem kell!
                   |
                   |Csak a hold az égen,
                   |Csak a nap ragyogjon,
                   |Simogasson, ha arcomhoz ér.
                   |Csak a hold ragyogjon,
                   |Csak a nap az égen,
                   |Nekem semmi más nem kell!
                   |
                   |Türürürürü türürürürüüüü...  """.stripMargin
}
