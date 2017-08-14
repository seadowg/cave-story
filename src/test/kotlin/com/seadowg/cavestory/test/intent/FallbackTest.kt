package com.seadowg.cavestory.test.intent

import com.jayway.jsonpath.JsonPath
import com.seadowg.cavestory.App
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.Okio
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class FallbackTest {

    private var app: App? = null

    @Before
    fun setup() {
        app = App()
        app?.run()
    }

    @After
    fun teardown() {
        app?.kill()
    }

    @Test
    fun test() {
        val requestStream = this.javaClass.getResourceAsStream("/fixtures/unknown_request.json")
        val requestJSON = Okio.buffer(Okio.source(requestStream)).readUtf8()

        val request = Request.Builder()
                .post(RequestBody.create(MediaType.parse("application/json"), requestJSON))
                .url("http://localhost:8080")
                .build()

        val rawResponse = OkHttpClient().newCall(request).execute()

        assertThat(rawResponse.code()).isEqualTo(200)
        assertThat(rawResponse.header("Content-Type")).isEqualTo("application/json")

        val responseString = rawResponse.body()!!.string()
        val response = JsonPath.parse(responseString)

        assertThat(response.read<String>("$.speech")).isEqualTo("I don't understand. Why don't you try looking around?")
        assertThat(response.read<String>("$.contextOut[0].name")).isEqualTo("in_waterfall")
        assertThat(response.read<Int>("$.contextOut[0].lifespan")).isEqualTo(1)
    }
}