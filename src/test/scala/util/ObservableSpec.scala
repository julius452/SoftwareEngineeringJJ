package util

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ObservableSpec extends AnyFlatSpec with Matchers {
  val observable = new Observable
  val observer = new Observer {
    var updated: Boolean = false
    def isUpdated: Boolean = updated
    override def update(): Unit = updated = true
  }

  "An Observable" should "add an Observer" in {
    observable.add(observer)
    observable.observers should contain(observer)
  }

  it should "notify an Observer" in {
    observable.add(observer)
    observer.isUpdated should be(false)
    observable.notifyObservers()
    observer.isUpdated should be(true)
  }

  it should "remove an Observer" in {
    observable.add(observer)
    observable.remove(observer)
    observable.observers should not contain observer
  }
}