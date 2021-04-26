package br.com.zup.model.request

import br.com.zup.GrpcAccountType
import br.com.zup.GrpcKeyType
import br.com.zup.GrpcNewPixKeyRequest
import br.com.zup.annotation.ValidPixKey
import br.com.zup.model.enums.KeyType
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
class NewPixKeyRequest(
    @field:NotNull
    val accountType: GrpcAccountType?,
    @field:Size(max = 77)
    val keyValue: String?,
    @field:NotNull
    val keyType: KeyType?
) {

    fun toGrpcNewPixKeyRequest(clientIId: UUID): GrpcNewPixKeyRequest {
        return GrpcNewPixKeyRequest.newBuilder()
            .setClientId(clientIId.toString())
            .setKeyType(keyType?.grpcKeyType ?: GrpcKeyType.UNKNOWN_KEY_TYPE)
            .setKeyValue(keyValue ?: "")
            .setAccountType(accountType ?: GrpcAccountType.UNKNOWN_ACCOUNT_TYPE)
            .build()
    }

}