package de.mobimeo.controllers

import com.typesafe.scalalogging.LazyLogging
import de.mobimeo.services.DataProvider
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class AppController @Inject()(cc: ControllerComponents, dataProvider: DataProvider)
  extends AbstractController(cc) with LazyLogging {

  def lines(timestamp: String, x: Integer, y: Integer): Action[AnyContent] = Action.async {
    // Always return a list, even if empty. Could also return 404 if none is found, up to the requirements
    val result = dataProvider.vehiclesAt(timestamp, x, y)
    Future.successful(Ok(Json.toJson(result)))
  }

  def lineDelays(name: String): Action[AnyContent] = Action.async {
    val result = dataProvider.isDelayed(name)
    val response = result.map(v => Ok(Json.toJson(v))).getOrElse(NotFound)
    Future.successful(response)
  }
}
