package com.seadowg.cavestory.jvm

import com.seadowg.cavestory.system.EnvPort

class JvmSystemPort : EnvPort {
    override fun get(): Int {
        return try {
            Integer.parseInt(System.getenv("PORT"))
        } catch (e: NumberFormatException) {
            8080
        }

    }

}