package controllers

import controllers.HealthCheckController
import play.api.test.{FakeRequest, PlaySpecification, WithApplication}

class HealthCheckControllerSpec extends PlaySpecification {

  "GET /healthCheck" should {
    "GET 200 OK with body \"I'm alive!\"" in new WithApplication() {

      val response = new HealthCheckController().healthCheck().apply(FakeRequest())

      status(response) must beEqualTo(OK)
      contentAsString(response) must beEqualTo("I'm alive!")

    }
  }

}
