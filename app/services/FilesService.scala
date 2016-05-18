package services


class FilesService {

  def load(filePath:String) = scala.io.Source.fromFile(filePath)

  def loadAsString(filePath:String): String = {
    load(filePath).mkString
  }

}
