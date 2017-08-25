package com.seadowg.cavestory.system

class JvmSystemPort {
    fun get(): Int {
        return try {
            Integer.parseInt(System.getenv("PORT"))
        } catch (e: NumberFormatException) {
            8080
        }

    }

}