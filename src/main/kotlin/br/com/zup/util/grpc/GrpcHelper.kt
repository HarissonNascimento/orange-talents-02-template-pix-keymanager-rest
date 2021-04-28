package br.com.zup.util.grpc

import br.com.zup.GrpcCreatedAt
import br.com.zup.GrpcListPixKeyByClientIdResponse
import br.com.zup.GrpcPixKey
import br.com.zup.GrpcQueryPixKeyByClientIdAndPixIdResponse
import br.com.zup.model.enums.KeyType
import br.com.zup.model.response.BankAccount
import br.com.zup.model.response.ListPixKeyResponse
import br.com.zup.model.response.QueryPixKeyByClientIdAndPixIdResponse
import br.com.zup.model.response.QueryPixKeyResponse
import java.time.LocalDateTime
import java.util.*

fun GrpcQueryPixKeyByClientIdAndPixIdResponse.toQueryDataResponse(): QueryPixKeyByClientIdAndPixIdResponse {

    val bankAccount = BankAccount(
        participant = bankAccount.participant,
        branch = bankAccount.branch,
        accountNumber = bankAccount.accountNumber,
        accountType = bankAccount.accountType
    )

    return QueryPixKeyByClientIdAndPixIdResponse(
        pixId = UUID.fromString(pixId),
        clientId = UUID.fromString(clientId),
        keyType = KeyType.valueOf(keyType.name),
        keyValue = keyValue,
        ownerName = ownerName,
        ownerCPF = ownerCPF,
        bankAccount = bankAccount,
        createdAt = grpcCreatedAtToLocalDateTime(createdAt)
    )
}

fun GrpcListPixKeyByClientIdResponse.toListQueryDataResponse(): ListPixKeyResponse {

    val pixKeyList = mutableListOf<QueryPixKeyResponse>()

    pixKeysList.forEach {
        pixKeyList.add(toQueryPixKeyResponseByGrpcPixKey(it))
    }

    return ListPixKeyResponse(
        clientId = UUID.fromString(clientId),
        pixKeys = pixKeyList
    )
}

private fun toQueryPixKeyResponseByGrpcPixKey(grpcPixKey: GrpcPixKey): QueryPixKeyResponse {

    val createdAt = grpcCreatedAtToLocalDateTime(grpcPixKey.createdAt)

    return QueryPixKeyResponse(
        pixId = UUID.fromString(grpcPixKey.pixId),
        keyType = KeyType.valueOf(grpcPixKey.keyType.name),
        keyValue = grpcPixKey.keyValue,
        accountType = grpcPixKey.accountType,
        createdAt = createdAt
    )
}

private fun grpcCreatedAtToLocalDateTime(grpcCreatedAt: GrpcCreatedAt): LocalDateTime {
    return LocalDateTime.of(
        grpcCreatedAt.year,
        grpcCreatedAt.month,
        grpcCreatedAt.day,
        grpcCreatedAt.hour,
        grpcCreatedAt.minute,
        grpcCreatedAt.second
    )
}