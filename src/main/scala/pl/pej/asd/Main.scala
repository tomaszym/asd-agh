package pl.pej.asd

import java.io.File
import java.io._

import pl.pej.asd.huf.{Huffman, FrequencyCount}

import scala.io.{BufferedSource, Source}
import rapture.io._
import rapture.codec._
import encodings.`UTF-8`
import rapture.core._
import rapture.fs._

object Main {
   def main(args: Array[String]): Unit = {

     args match {
       case Array(sourcePath, destinationPath) =>

         def source: BufferedSource = Source.fromURL(getClass.getResource("/seneca.txt"))
         def raptureReader = ReaderBuilder.input(source.reader)
         import InputBuilder._


         val codeTree = Huffman.buildCodeTree(FrequencyCount(raptureReader,1))

         val res = Huffman.encode(codeTree,raptureReader,1)


         val dst = new File("/home/tomaszym/git/asd-agh/senecaEncoded")
         val writer = new BufferedOutputStream(new FileOutputStream(dst))

         writer.write(res.dataAsByteArray)

         val builder = StringBuilder.newBuilder

         Huffman.decode(codeTree, res, builder)

         println(builder.take(200))


       case _: Array[String] => println("Execute the programme with source path and destination path as it's arguments. Over. ")
     }




   }
 }
