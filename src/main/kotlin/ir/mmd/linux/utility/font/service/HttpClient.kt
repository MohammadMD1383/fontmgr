package ir.mmd.linux.utility.font.service

import io.ktor.client.*
import io.ktor.client.engine.cio.*

val httpClient = HttpClient(CIO) {
	expectSuccess = true
}
