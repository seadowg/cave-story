package com.seadowg.cavestory.apiai

import com.seadowg.cavestory.js.Params

data class Request(val params: Params, val contexts: List<Context>)
