package model.helpers

trait TestRunner {

  def run[T]()(tests: => T): T = {
      tests
  }

}
