package br.com.zup.controller

import br.com.zup.*
import br.com.zup.config.factory.KeymanagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class QueryPixKeyControllerTest {

    companion object {
        val CLIENT_ID = UUID.randomUUID().toString()
        val PIX_ID = UUID.randomUUID().toString()
        val EMAIL = GrpcKeyType.EMAIL
        const val EMAIL_VALUE = "teste@email.com"
        const val CPF_VALUE = "11111111111"
        val ACCOUNT_TYPE = GrpcAccountType.CONTA_CORRENTE
        val CREATED_AT: GrpcCreatedAt = LocalDateTime.now().let {
            GrpcCreatedAt.newBuilder()
                .setDay(it.dayOfMonth)
                .setMonth(it.monthValue)
                .setYear(it.year)
                .setHour(it.hour)
                .setMinute(it.minute)
                .setSecond(it.second)
                .build()
        }
    }

    @field:Inject
    lateinit var queryPixKeyStub: KeymanagerQueryDataServiceGrpc.KeymanagerQueryDataServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `must return pix key`() {

        val grpcRequest = GrpcQueryPixKeyByClientIdAndPixIdRequest.newBuilder()
            .setPixId(PIX_ID)
            .setClientId(CLIENT_ID)
            .build()

        given(queryPixKeyStub.queryPixKeyByClientIdAndPixId(grpcRequest))
            .willReturn(generateGrpcQueryPixKeyByClientIdAndPixIdResponse())

        val httpRequest = HttpRequest.GET<Any>("/api/v1/client/$CLIENT_ID/pix/$PIX_ID")
        val httpResponse = client.toBlocking().exchange(httpRequest, Any::class.java)

        with(httpResponse) {
            assertEquals(HttpStatus.OK, status)
            assertNotNull(body())
        }

    }

    @Test
    fun `must return all pix keys by client id`() {

        val grpcRequest = GrpcListPixKeyByClientIdRequest.newBuilder()
            .setClientId(CLIENT_ID)
            .build()

        given(queryPixKeyStub.listPixKeyByClientId(grpcRequest))
            .willReturn(generateGrpcListPixKeyByClientIdResponse())

        val httpRequest = HttpRequest.GET<Any>("/api/v1/client/$CLIENT_ID/pix/all")
        val httpResponse = client.toBlocking().exchange(httpRequest, List::class.java)

        with(httpResponse) {
            assertEquals(HttpStatus.OK, status)
            assertNotNull(body())
            assertEquals(body().size, 1)
        }

    }

    @Factory
    @Replaces(factory = KeymanagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubQueryPixKey(): KeymanagerQueryDataServiceGrpc.KeymanagerQueryDataServiceBlockingStub {
            return Mockito.mock(KeymanagerQueryDataServiceGrpc.KeymanagerQueryDataServiceBlockingStub::class.java)
        }

    }

    private fun generateGrpcQueryPixKeyByClientIdAndPixIdResponse(): GrpcQueryPixKeyByClientIdAndPixIdResponse {

        val bankAccount = GrpcBankAccount.newBuilder()
            .setParticipant("Institute Test")
            .setBranch("000000")
            .setAccountNumber("111111111")
            .setAccountType(GrpcAccountType.CONTA_CORRENTE)
            .build()

        return GrpcQueryPixKeyByClientIdAndPixIdResponse.newBuilder()
            .setPixId(PIX_ID)
            .setClientId(CLIENT_ID)
            .setKeyType(EMAIL)
            .setKeyValue(EMAIL_VALUE)
            .setOwnerName("Client Test")
            .setOwnerCPF(CPF_VALUE)
            .setBankAccount(bankAccount)
            .setCreatedAt(CREATED_AT)
            .build()
    }

    private fun generateGrpcListPixKeyByClientIdResponse(): GrpcListPixKeyByClientIdResponse {


        val grpcPixKey = GrpcPixKey.newBuilder()
            .setPixId(PIX_ID)
            .setKeyType(EMAIL)
            .setKeyValue(EMAIL_VALUE)
            .setAccountType(ACCOUNT_TYPE)
            .setCreatedAt(CREATED_AT)
            .build()

        return GrpcListPixKeyByClientIdResponse.newBuilder()
            .setClientId(CLIENT_ID)
            .addAllPixKeys(listOf(grpcPixKey))
            .build()

    }

}