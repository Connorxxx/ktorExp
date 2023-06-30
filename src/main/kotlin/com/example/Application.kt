package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

fun main() {
    configServer.start(wait = true)
}

private val configServer by lazy {
    embeddedServer(
        Netty,
        host = "0.0.0.0",
        port = 8089,
        configure = NettyApplicationEngine.Configuration::configure,
        module = Application::module
    )
}

fun NettyApplicationEngine.Configuration.configure() {
    connectionGroupSize = 2
    workerGroupSize = 5
    callGroupSize = 10
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
