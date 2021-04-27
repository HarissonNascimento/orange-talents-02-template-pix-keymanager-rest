package br.com.zup.model.request

import br.com.zup.GrpcRemovePixKeyRequest
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class RemovePixKeyRequest(
    @field:NotBlank
    @field:Size(max = 77)
    val pixId: String
) {

    fun toGrpcRemovePixKeyRequest(clientId: UUID): GrpcRemovePixKeyRequest {
        return GrpcRemovePixKeyRequest.newBuilder()
            .setPixId(pixId)
            .setClientId(clientId.toString())
            .build()
    }

}