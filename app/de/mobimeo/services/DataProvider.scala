package de.mobimeo.services

import com.google.inject.ImplementedBy
import de.mobimeo.domain.Stop
import de.mobimeo.services.DataProvider._
import javax.inject.Singleton

import scala.io.Source

@ImplementedBy(classOf[DataProviderImpl])
trait DataProvider {
  def vehiclesAt(timestamp: String, x: Int, y: Int): List[String]
  def isDelayed(line: String): Option[Boolean]
}

trait DataReader {
  // Assumptions:
  //  - the data does not change with time, thus "caching" it in variables
  //  - to keep it simple, the data is not validated. This is very tricky of course, and validation
  //    is important for "real" apps

  protected val delayByLine: Map[String, Int] = {
    val dataLines = Source.fromResource("data/delays.csv").getLines().drop(HeaderLine)
    dataLines.flatMap (
      _.split(Comma) match {
        case Array(line, delay) => Some(line -> delay.toInt)
        case _ => None
    }).toMap
  }

  protected val lineById: Map[Int, String] = {
    val dataLines = Source.fromResource("data/lines.csv").getLines().drop(HeaderLine)
    dataLines.flatMap (
      _.split(Comma) match {
        case Array(lineId, lineName) => Some(lineId.toInt -> lineName)
        case _ => None
      }).toMap
  }

  protected val stopById: Map[Int, Stop] = {
    val dataLines = Source.fromResource("data/stops.csv").getLines().drop(HeaderLine)
    dataLines.flatMap (
      _.split(Comma) match {
        case Array(stopId, x, y) => Some(stopId.toInt -> Stop(stopId.toInt, x.toInt, y.toInt))
        case _ => None
      }).toMap
  }

  protected val lineAndStopByTime: Map[String, List[(Int, Int)]] = {
    val dataLines = Source.fromResource("data/times.csv").getLines().drop(HeaderLine)
    val result = Map()
    val parsedValues = dataLines.flatMap (
      _.split(Comma) match {
        case Array(lineIdStr, stopIdStr, time) =>
          val lineId = lineIdStr.toInt
          val correctedTime = correctMinutes(time, lineId)
          Some(correctedTime -> (lineId, stopIdStr.toInt))
        case _ => None
      }).toList
    parsedValues.groupBy(_._1).mapValues(_.map(_._2))
  }

  def correctMinutes(time: String, lineId: Int): String = {
    // This correction is simplified and only works with the test data where adding a delay
    // does not switch to the new hour. Otherwise proper parsing of the time would be required
    val maybeDelay = lineById.get(lineId).flatMap(delayByLine.get)
    maybeDelay.map(delay => {
      val correctedMins = time.substring(3, 5).toInt + delay
      s"${time.substring(0, 2)}:$correctedMins:${time.substring(6, 8)}"
    }).getOrElse(time)
  }
}

@Singleton
class DataProviderImpl extends DataProvider with DataReader {

  override def isDelayed(name: String): Option[Boolean] = {
    delayByLine.get(name).map(_ > 0)
  }

  override def vehiclesAt(timestamp: String, x: Int, y: Int): List[String] = {
    // Find all the stops at the given coordinates. Then find all combinations
    // of lines and stops at the given timestamp, and find the match
    val stops = stopById.values.toList.filter(s => s.x == x && s.y == y)
    val lineIdsFound = lineAndStopByTime.getOrElse(timestamp, Nil).filter(entry => stops.exists(_.id == entry._2)).map(_._1)
    lineIdsFound.flatMap(id => lineById.get(id))
  }
}

object DataProvider {
  val Comma = ","
  val HeaderLine = 1
}
