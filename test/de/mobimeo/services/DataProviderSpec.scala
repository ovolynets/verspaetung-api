package de.mobimeo.services

import org.scalatest.{FlatSpec, MustMatchers}

class DataProviderSpec extends FlatSpec with MustMatchers {

  private val lineM4 = "M4"
  private val lineSomething = "something"
  private val dataProvider = new DataProviderImpl()

  it should "return correctly return that M4 is delayed" in {
    val result = dataProvider.isDelayed(lineM4)
    result must be (Some(true))
  }

  it should "return nothing for non-existing line" in {
    val result = dataProvider.isDelayed(lineSomething)
    result must be (None)
  }
}
