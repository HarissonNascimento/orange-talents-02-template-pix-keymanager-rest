package br.com.zup.controller

import br.com.zup.GrpcRemovePixKeyResponse
import br.com.zup.KeymanagerRemoveServiceGrpc
import br.com.zup.config.factory.KeymanagerGrpcFactory
import br.com.zup.model.request.RemovePixKeyRequest
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RemovePixKeyControllerTest {

    @field:Inject
    lateinit var removePixKeyStub: KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `must remove pix key`() {

        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val grpcResponse = GrpcRemovePixKeyResponse.newBuilder()
            .setClientId(clientId)
            .setPixId(pixId)
            .build()

        given(removePixKeyStub.removePixKey(Mockito.any()))
            .willReturn(grpcResponse)

        val removePixKeyRequest = RemovePixKeyRequest(pixId = pixId)

        val httpDeleteRequest = HttpRequest.DELETE("/api/v1/client/$clientId/remove-pix", removePixKeyRequest)
        val httpDeleteResponse = client.toBlocking().exchange(httpDeleteRequest, RemovePixKeyRequest::class.java)

        with(httpDeleteResponse) {
            assertEquals(HttpStatus.NO_CONTENT, status)
        }
    }

    @Factory
    @Replaces(factory = KeymanagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubRemovePixKey(): KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub {
            return Mockito.mock(KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub::class.java)
        }

    }

}