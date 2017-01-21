
package services

import javax.inject.Inject

import play.api.libs.json.JsValue
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

trait GifService {
	def getGifs(term: String) : Future[JsValue]
}

class GiphyService @Inject() (ws: WSClient) (implicit context: ExecutionContext) extends GifService{
	override def getGifs(term: String): Future[JsValue] = {
		val query = term.replace(" ", "+")
		val apiKey = "dc6zaTOxFJmzC"
		val giphyUrl = s"http://api.giphy.com/v1/gifs/search?q=${query}&limit=5&api_key=${apiKey}"

		val request: WSRequest = ws.url(giphyUrl)

		// API call
		val futureResponse: Future[WSResponse] = request.get()

		// Convert to Json
		val response: Future[JsValue] = futureResponse.map((response: WSResponse) => {
			response.json
		})

		return response
	}
}