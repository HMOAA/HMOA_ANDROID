package com.hmoa.core_network.authentication

import com.hmoa.core_model.request.RememberedLoginRequestDto
import io.ktor.client.statement.*

interface RefreshTokenManager {

    suspend fun refreshAuthToken(dto: RememberedLoginRequestDto): HttpResponse
}