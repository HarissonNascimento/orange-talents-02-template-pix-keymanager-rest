package br.com.zup.model.response

import br.com.zup.GrpcAccountType
import br.com.zup.model.enums.KeyType
import java.time.LocalDateTime
import java.util.*

class QueryPixKeyByClientIdAndPixIdResponse(
    val pixId: UUID?,
    val clientId: UUID?,
    val keyType: KeyType?,
    val keyValue: String?,
    val ownerName: String?,
    val ownerCPF: String?,
    val bankAccount: BankAccount?,
    val createdAt: LocalDateTime?
)

class BankAccount(
    val participant: String?,
    val branch: String?,
    val accountNumber: String?,
    val accountType: GrpcAccountType?
)
