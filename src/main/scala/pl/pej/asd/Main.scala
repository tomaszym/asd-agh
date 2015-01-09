package pl.pej.asd

import java.io.File

import pl.pej.asd.huf.Huffman

import scala.io.Source

object Main {
   def main(args: Array[String]): Unit = {



     val data = Source.fromURL(new File("/home/tomaszym/git/asd-agh/src/main/resources/seneca.txt").toURL).mkString + " "

     Huffman.encode(data)

   }
 }
