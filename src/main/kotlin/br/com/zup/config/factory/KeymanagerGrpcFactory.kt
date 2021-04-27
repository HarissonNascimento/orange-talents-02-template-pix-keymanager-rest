package br.com.zup.config.factory

import br.com.zup.KeymanagerQueryDataServiceGrpc
import br.com.zup.KeymanagerRegisterServiceGrpc
import br.com.zup.KeymanagerRemoveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeymanagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun createNewKey(): KeymanagerRegisterServiceGrpc.KeymanagerRegisterServiceBlockingStub {
        return KeymanagerRegisterServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun removePixKey(): KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub {
        return KeymanagerRemoveServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun queryPixKeyByClientIdAndPixId(): KeymanagerQueryDataServiceGrpc.KeymanagerQueryDataServiceBlockingStub {
        return KeymanagerQueryDataServiceGrpc.newBlockingStub(channel)
    }

}