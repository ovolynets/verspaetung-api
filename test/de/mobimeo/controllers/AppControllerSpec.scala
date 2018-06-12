package de.mobimeo.controllers

import de.mobimeo.services.DataProviderImpl
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.PlaySpec
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsArray, JsString}
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers._

class AppControllerSpec extends PlaySpec with Results with BeforeAndAfterAll {

  private val app = GuiceApplicationBuilder().build()

  private val controllerComponents = app.injector.instanceOf[ControllerComponents]

  private val dataProvider = new DataProviderImpl()
  val appController = new AppController(controllerComponents, dataProvider)

  "AppController" should {
    "return 404 for an unknown line" in {
      val response = appController.lineDelays("something")(FakeRequest())
      status(response) mustBe NOT_FOUND
    }

    "return true for M4" in {
      val response = appController.lineDelays("M4")(FakeRequest())
      status(response) mustBe OK
      contentAsString(response) mustEqual "true"
    }

    "find M4 at the right time and place" in {
      val response = appController.lines("10:10:00", 2, 9)(FakeRequest())
      status(response) mustBe OK
      contentAsJson(response).asInstanceOf[JsArray].value(0).toString() mustEqual "\"200\""
    }
  }
}
