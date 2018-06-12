package de.mobimeo.services

import org.scalatest.{FlatSpec, MustMatchers}

class DataReaderSpec extends FlatSpec with MustMatchers {

  private val dataReader = new DataReader {}
  private val S75Delay = 2

  it should "correctly add delay" in {
    dataReader.correctMinutes("12:34:56", S75Delay) mustEqual "12:44:56"
  }

}
