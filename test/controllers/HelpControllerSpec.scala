package controllers

import play.api.test.{FakeRequest, PlaySpecification, WithApplication}
import services.FilesService

class HelpControllerSpec extends PlaySpecification {

  "/help" should {
    "GET 200 OK with Help message in plain text" in new WithApplication() {

      val fs = new FilesService
      val expectedMessage = fs.loadAsString("public/messages/help.txt")

      val response = new HelpController(fs).index().apply(FakeRequest())

      status(response) must beEqualTo(OK)
      contentType(response).get must beEqualTo("text/plain")

      contentAsString(response) must beEqualTo(expectedMessage)

    }
  }
}
