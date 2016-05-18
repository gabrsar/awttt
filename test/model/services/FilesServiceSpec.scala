package model.services

import play.api.test.{PlaySpecification, WithApplication}
import services.FilesService

class FilesServiceSpec extends PlaySpecification {

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
