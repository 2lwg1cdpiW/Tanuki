package org.tanukis.tanuki.sync.data

import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.tanukis.tanuki.core.exceptions.SyncApiException
import org.tanukis.tanuki.core.network.BaseHttpClient
import org.tanukis.tanuki.core.util.ext.toRequestBody
import org.tanukis.tanuki.parsers.util.await
import org.tanukis.tanuki.parsers.util.parseJson
import org.tanukis.tanuki.parsers.util.parseRaw
import org.tanukis.tanuki.parsers.util.removeSurrounding
import javax.inject.Inject

@Reusable
class SyncAuthApi @Inject constructor(
	@BaseHttpClient private val okHttpClient: OkHttpClient,
) {

	suspend fun authenticate(syncURL: String, email: String, password: String): String {
		val body = JSONObject(
			mapOf("email" to email, "password" to password),
		).toRequestBody()
		val request = Request.Builder()
			.url("$syncURL/auth")
			.post(body)
			.build()
		val response = okHttpClient.newCall(request).await()
		if (response.isSuccessful) {
			return response.parseJson().getString("token")
		} else {
			val code = response.code
			val message = response.parseRaw().removeSurrounding('"')
			throw SyncApiException(message, code)
		}
	}
}
