package br.com.zup.model.response

import br.com.zup.GrpcAccountType
import br.com.zup.model.enums.KeyType
import java.time.LocalDateTime
import java.util.*

class ListPixKeyResponse(
    val clientId: UUID,
    val pixKeys: List<QueryPixKeyResponse>
)

class QueryPixKeyResponse(
    val pixId: UUID,
    val keyType: KeyType,
    val keyValue: String,
    val accountType: GrpcAccountType,
    val createdAt: LocalDateTime
)