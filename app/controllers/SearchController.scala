package controllers

import javax.inject._

import play.api.libs.json.{JsObject, _}
import play.api.mvc._
import services.GifService

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SearchController @Inject() (gifService: GifService) (implicit context: ExecutionContext) extends Controller {

	def search(term: String) = Action.async {

		val response: Future[JsValue] = gifService.getGifs(term)

		response.map((json: JsValue) => {

			val count = (json \ "pagination" \ "total_count").get.as[Int]

			if (count < 5) {

				val result = JsObject(Seq("data" -> JsArray()))
				Ok(result)

			} else {
				// Initialize the collection
				val resList: ArrayBuffer[JsObject] = ArrayBuffer[JsObject]()

				// Populate the collection with the chosen Json fields
				(json \ "data").as[Seq[JsObject]].map({(gifSource: JsObject) =>
					resList += Json.obj("gif_id" -> (gifSource \ "id").as[String],
						"url" -> (gifSource \ "url").as[String])
				})

				// Return the Json after prepending it with the appropriate property
				Ok(JsObject(Seq("data" -> Json.toJson(resList))))
			}
		})

	}

}
