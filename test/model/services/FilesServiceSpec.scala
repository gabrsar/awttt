package model.services

import org.specs2.mutable._
import play.api.test.WithApplication
import services.FilesService


class FilesServiceSpec extends Specification {

  "FilesService" should {

    val alphabetInputFile = "test/resources/alphabet.txt"

    val alphabetContent =
      s"""ABCDEFGHIJKLM
         |NOPQRSTUVWXYZ""".stripMargin

    ".load as BufferedSource" in new WithApplication() {
      val fs = new FilesService
      val bufferedSource = fs.load(alphabetInputFile)
      bufferedSource.mkString must beEqualTo(alphabetContent)
    }

    ".loadAsString as String" in new WithApplication() {
      val fs = new FilesService
      fs.loadAsString(alphabetInputFile) must beEqualTo(alphabetContent)
    }

  }

}
