package com.seadowg.cavestory.apiai

interface ActionHandler {
    fun handle(request: Request): Response
}
