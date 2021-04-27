package br.com.zup.controller

import br.com.zup.KeymanagerRemoveServiceGrpc
import br.com.zup.model.request.RemovePixKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1/client/{clientId}")
class RemovePixKeyController(@Inject val grpcClient: KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub) {

    @Delete("/remove-pix")
    fun removePixKey(clientId: UUID, @Body @Valid request: RemovePixKeyRequest): HttpResponse<Any> {

        val grpcRequest = request.toGrpcRemovePixKeyRequest(clientId)

        grpcClient.removePixKey(grpcRequest)

        return HttpResponse.noContent()

    }
}