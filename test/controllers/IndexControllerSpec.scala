package controllers

import play.api.test.{FakeRequest, PlaySpecification, WithApplication}
import services.FilesService

class IndexControllerSpec extends PlaySpecification {

  "/" should {
    "GET 200 OK with Hello message in plain text" in new WithApplication() {

      val fs = new FilesService
      val expectedMessage = fs.loadAsString("public/messages/hello.txt")

      val response = new IndexController(fs).index().apply(FakeRequest())

      status(response) must beEqualTo(OK)
      contentType(response).get must beEqualTo("text/plain")

      contentAsString(response) must beEqualTo(expectedMessage)

    }
  }


}
