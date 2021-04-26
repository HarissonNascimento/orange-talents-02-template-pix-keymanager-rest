package br.com.zup.controller

import br.com.zup.GrpcAccountType
import br.com.zup.GrpcCreatedAt
import br.com.zup.GrpcNewPixKeyResponse
import br.com.zup.KeymanagerRegisterServiceGrpc
import br.com.zup.config.factory.KeymanagerGrpcFactory
import br.com.zup.model.enums.KeyType
import br.com.zup.model.request.NewPixKeyRequest
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class NewPixKeyControllerTest {

    @field:Inject
    lateinit var nexPixKeyStub: KeymanagerRegisterServiceGrpc.KeymanagerRegisterServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `must register pix key`() {

        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
        val createdAt = LocalDateTime.now()

        val grpcNewPixKeyResponse = GrpcNewPixKeyResponse.newBuilder()
            .setClientId(clientId)
            .setPixId(pixId)
            .setCreatedAt(generateCreatedAtByLocalDateTime(createdAt))
            .build()

        given(nexPixKeyStub.createNewKey(Mockito.any())).willReturn(grpcNewPixKeyResponse)

        val newPixKeyRequest = NewPixKeyRequest(
            accountType = GrpcAccountType.CONTA_CORRENTE,
            keyValue = "test@email.com",
            keyType = KeyType.EMAIL
        )

        val httpRequest = HttpRequest.POST("/api/v1/client/$clientId/register-pix", newPixKeyRequest)
        val httpResponse = client.toBlocking().exchange(httpRequest, NewPixKeyRequest::class.java)

        with(httpResponse) {
            assertEquals(HttpStatus.CREATED, status)
            assertTrue(headers.contains("Location"))
            assertTrue(header("Location")!!.contains(pixId))
        }

    }

    @Factory
    @Replaces(factory = KeymanagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubCreateNewKey(): KeymanagerRegisterServiceGrpc.KeymanagerRegisterServiceBlockingStub {
            return Mockito.mock(KeymanagerRegisterServiceGrpc.KeymanagerRegisterServiceBlockingStub::class.java)
        }

    }

    private fun generateCreatedAtByLocalDateTime(localDateTime: LocalDateTime): GrpcCreatedAt {
        return GrpcCreatedAt.newBuilder()
            .setDay(localDateTime.dayOfMonth)
            .setMonth(localDateTime.monthValue)
            .setYear(localDateTime.year)
            .setHour(localDateTime.hour)
            .setMinute(localDateTime.minute)
            .setSecond(localDateTime.second)
            .build()
    }

}