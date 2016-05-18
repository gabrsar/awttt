package model.controllers

import play.api.test.{FakeRequest, PlaySpecification, WithApplication}

class HealthCheckControllerSpec extends PlaySpecification {

  "GET /healthCheck" should {
    "200 OK with body \"I'm alive!\"" in new WithApplication() {

      val request = FakeRequest(GET, "/healthCheck")
      val response = route(request).get

      status(response) must beEqualTo(OK)
      contentAsString(response) must beEqualTo("I'm alive!")

    }
  }

}
