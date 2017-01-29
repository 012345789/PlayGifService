import org.scalatestplus.play._
import play.api.libs.json._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Your new application is ready.")
    }

  }

  "CountController" should {

    "return an increasing count" in {
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "0"
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "1"
      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "2"
    }

  }

  "SearchController" should {

    "return no data if count is less than 5" in {
      contentAsJson(route(app, FakeRequest(GET, "/search/sro3kdmfmgido")).get) mustBe Json.parse("""{"data": []}""")
    }

    "return data of 5 length if count is 5 or greater" in {
      val data = ((contentAsJson(route(app, FakeRequest(GET, "/search/cats")).get) \ "data").get).asInstanceOf[JsArray].value
      data.size mustBe 5
    }
  }

}
