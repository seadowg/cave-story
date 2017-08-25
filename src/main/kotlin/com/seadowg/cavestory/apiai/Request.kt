package com.seadowg.cavestory.apiai

data class Request(val action: String, val params: Map<String, String>, val contexts: List<Context>)
