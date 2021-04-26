package br.com.zup.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GlobalExceptionHandlerTest {

    private val genericRequest = HttpRequest.GET<Any>("/")

    @Test
    fun `should return 404 when statusException is not found`() {

        val message = "Not Found"

        val notFoundException = StatusRuntimeException(
            Status.NOT_FOUND
                .withDescription(message)
        )

        val response = GlobalExceptionHandler().handle(genericRequest, notFoundException)

        assertEquals(HttpStatus.NOT_FOUND, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)

    }

    @Test
    fun `must return 422 when statusException is already exist`() {

        val message = "Pix key already exists"
        val alreadyExistsException = StatusRuntimeException(
            Status.ALREADY_EXISTS
                .withDescription(message)
        )

        val response = GlobalExceptionHandler().handle(genericRequest, alreadyExistsException)

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    internal fun `should return 400 when statusException is invalid argument`() {

        val message = "Dados inválidos!"
        val invalidArgumentException = StatusRuntimeException(
            Status.INVALID_ARGUMENT
                .withDescription(message)
        )

        val response = GlobalExceptionHandler().handle(genericRequest, invalidArgumentException)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    internal fun `must return 500 when any other error is released`() {

        val internalException = StatusRuntimeException(Status.INTERNAL)

        val response = GlobalExceptionHandler().handle(genericRequest, internalException)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
        assertNotNull(response.body())
        assertTrue((response.body() as JsonError).message.contains("INTERNAL"))
    }

}