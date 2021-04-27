package br.com.zup.util.grpc

import br.com.zup.GrpcQueryPixKeyByClientIdAndPixIdResponse
import br.com.zup.model.enums.KeyType
import br.com.zup.model.response.BankAccount
import br.com.zup.model.response.QueryPixKeyByClientIdAndPixIdResponse
import java.time.LocalDateTime
import java.util.*

fun GrpcQueryPixKeyByClientIdAndPixIdResponse.toQueryDataResponse(): QueryPixKeyByClientIdAndPixIdResponse {

    val bankAccount = BankAccount(
        participant = bankAccount.participant,
        branch = bankAccount.branch,
        accountNumber = bankAccount.accountNumber,
        accountType = bankAccount.accountType
    )

    val createdAt = LocalDateTime.of(
        createdAt.year,
        createdAt.month,
        createdAt.day,
        createdAt.hour,
        createdAt.minute,
        createdAt.second
    )

    return QueryPixKeyByClientIdAndPixIdResponse(
        pixId = UUID.fromString(pixId),
        clientId = UUID.fromString(clientId),
        keyType = KeyType.valueOf(keyType.name),
        keyValue = keyValue,
        ownerName = ownerName,
        ownerCPF = ownerCPF,
        bankAccount = bankAccount,
        createdAt = createdAt
    )
}