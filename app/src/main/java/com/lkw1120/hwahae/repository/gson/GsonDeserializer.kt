package com.lkw1120.hwahae.repository.gson

import com.google.gson.*
import com.lkw1120.hwahae.datasource.entity.Detail
import com.lkw1120.hwahae.datasource.entity.Product
import com.lkw1120.hwahae.datasource.remote.ApiResponse
import com.lkw1120.hwahae.datasource.remote.DetailResponse
import com.lkw1120.hwahae.datasource.remote.ErrorResponse
import com.lkw1120.hwahae.datasource.remote.ProductResponse
import java.lang.reflect.Type

class GsonDeserializer : JsonDeserializer<ApiResponse> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?): ApiResponse {
        val jsonObject: JsonObject = json.asJsonObject
        val statusCode: Int = jsonObject.get("statusCode").asInt
        lateinit var response:ApiResponse
        when(jsonObject.get("statusCode").asInt) {
            200  -> {
                if(jsonObject.get("body").isJsonArray) {
                    val jsonBody = jsonObject.get("body").asJsonArray
                    val items:MutableList<Product> = mutableListOf()
                    for(item in jsonBody) {
                        val body = item.asJsonObject
                        items.add(Product(
                            id = body.get("id").asInt,
                            thumbnail_image = body.get("thumbnail_image").asString,
                            title = body.get("title").asString,
                            price = body.get("price").asString,
                            oily_score = if(body.has("oily_score"))
                                body.get("oily_score").asInt else 0,
                            dry_score = if(body.has("dry_score"))
                                body.get("dry_score").asInt else 0,
                            sensitive_score = if(body.has("sensitive_score"))
                                body.get("sensitive_score").asInt else 0
                        ))
                    }
                    response = ProductResponse(statusCode,items,jsonObject.get("scanned_count").asInt)
                }
                else {
                    val jsonBody = jsonObject.get("body").asJsonObject
                    val item = Detail(
                        id = jsonBody.get("id").asInt,
                        full_size_image = jsonBody.get("full_size_image").asString,
                        title = jsonBody.get("title").asString,
                        description = jsonBody.get("description").asString.replace("""\n""","\n"),
                        price = jsonBody.get("price").asString,
                        oily_score = jsonBody.get("oily_score").asInt,
                        dry_score = jsonBody.get("dry_score").asInt,
                        sensitive_score = jsonBody.get("sensitive_score").asInt
                    )
                    response = DetailResponse(statusCode,item)
                }
            }
            else -> {
                response = ErrorResponse(statusCode,jsonObject.get("body").asString)
            }

        }
        return response
    }
}