package model.controllers

import com.google.common.net.MediaType
import play.api.test.{FakeRequest, PlaySpecification, WithApplication}
import services.FilesService

class IndexControllerSpec extends PlaySpecification {

  "/" should {
    "GET 200 OK with Hello message in plain text" in new WithApplication() {

      val fs = new FilesService
      val expectedMessage = fs.loadAsString("public/messages/hello.txt")

      val request = FakeRequest(GET,"/")
      val response = route(request).get

      status(response) must beEqualTo(OK)
      contentType(response) must beEqualTo(MediaType.PLAIN_TEXT_UTF_8)
      contentAsString(response) must beEqualTo(expectedMessage)

    }
  }


}
