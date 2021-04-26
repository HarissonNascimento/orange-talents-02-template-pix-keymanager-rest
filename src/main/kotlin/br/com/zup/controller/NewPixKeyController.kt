package br.com.zup.controller

import br.com.zup.KeymanagerRegisterServiceGrpc
import br.com.zup.model.request.NewPixKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1/client/{clientId}")
class NewPixKeyController(@Inject val grpcClient: KeymanagerRegisterServiceGrpc.KeymanagerRegisterServiceBlockingStub) {

    @Post("/register-pix")
    fun createNewKey(clientId: UUID, @Valid @Body request: NewPixKeyRequest): HttpResponse<Any> {

        val grpcRequest = request.toGrpcNewPixKeyRequest(clientId)

        val grpcResponse = grpcClient.createNewKey(grpcRequest)

        val location = HttpResponse.uri("/api/v1/client/$clientId/pix/${grpcResponse.pixId}")

        return HttpResponse.created(location)

    }

}