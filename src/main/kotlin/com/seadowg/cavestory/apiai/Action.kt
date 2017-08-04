package com.seadowg.cavestory.apiai

interface Action {
    fun handle(request: Request): Response
}
