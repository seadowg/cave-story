package com.seadowg.cavestory.test

import com.jayway.jsonpath.JsonPath
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.seadowg.cavestory.App
import com.seadowg.cavestory.engine.Script
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.*
import okio.Okio
import okio.Okio.*
import org.assertj.core.api.Assertions.assertThat

class PerformActionTest {

    private var app: App? = null
    private val script = Script()

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
        val requestStream = this.javaClass.getResourceAsStream("/fixtures/look_around_request.json")
        val requestJSON = buffer(source(requestStream)).readUtf8()

        val request = Request.Builder()
                .post(create(MediaType.parse("application/json"), requestJSON))
                .url("http://localhost:8080")
                .build()

        val responseString = OkHttpClient().newCall(request).execute().body()!!.string()
        val response = JsonPath.parse(responseString)

        assertThat(response.read<String>("$.speech")).isEqualTo(script.theCaveIsVeryDark)
        assertThat(response.read<String>("$.contextOut[0].name")).isEqualTo("in_cave_1")
        assertThat(response.read<Int>("$.contextOut[0].lifespan")).isEqualTo(1)
    }
}