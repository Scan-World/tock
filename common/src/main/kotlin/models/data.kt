package ai.tock.demo.common.models

import org.json.JSONObject


class ResponsePost(json: String) : JSONObject(json) {
    val data = this.optJSONArray("posts")
            ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
            ?.map { Post(it.toString()) } // transforms each JSONObject of the array into Foo
}

class Post(json: String) : JSONObject(json) {
    val id = this.optInt("id")
    val title: String? = this.optString("title")
    val content: String? = this.optString("content")
    val link: String? = this.optString("link")
    val updatedDate: String? = this.optString("updatedDate")
    val updatedHumanDate: String? = this.optString("updatedHumanDate")
}


class ResponsePostGoogle(json: String) : JSONObject(json) {
    val data = this.optJSONArray("data")
            ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } } // returns an array of JSONObject
            ?.map { PostGoogle(it.toString()) } // transforms each JSONObject of the array into Foo
}

class PostGoogle(json: String) : JSONObject(json) {
    val name: String? = this.optString("name")
    val url: String? = this.optString("url")
}
