package services

import scalax.io.Resource


class FilesService {

  def load(filePath: String) = scala.io.Source.fromFile(filePath)

  def loadAsString(filePath: String): String = {
    load(filePath).mkString
  }

  def save(filePath: String, content: String): Unit = {
    val f = Resource.fromFile(filePath)
    f.write(content)
  }

}
