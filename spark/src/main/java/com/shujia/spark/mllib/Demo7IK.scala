package com.shujia.spark.mllib

import java.io.StringReader

import org.wltea.analyzer.core.{IKSegmenter, Lexeme}

import scala.collection.mutable.ListBuffer

object Demo7IK {
  def main(args: Array[String]): Unit = {

    val text: String = "数加学院牛逼，的"

    println(fit(text))

  }

  def fit(text: String): List[String] = {

    val words: ListBuffer[String] = new ListBuffer[String]

    val reader: StringReader = new StringReader(text)

    val segmenter: IKSegmenter = new IKSegmenter(reader, true)

    //取第一个
    var lexeme: Lexeme = segmenter.next()

    while (lexeme != null) {

      val word: String = lexeme.getLexemeText

      words += word

      //取下一个
      lexeme = segmenter.next()
    }

    words.toList
  }

}
