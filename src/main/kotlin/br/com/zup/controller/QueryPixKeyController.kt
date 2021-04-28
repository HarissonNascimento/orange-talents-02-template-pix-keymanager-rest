package br.com.zup.controller

import br.com.zup.GrpcListPixKeyByClientIdRequest
import br.com.zup.GrpcQueryPixKeyByClientIdAndPixIdRequest
import br.com.zup.KeymanagerQueryDataServiceGrpc
import br.com.zup.util.grpc.toListQueryDataResponse
import br.com.zup.util.grpc.toQueryDataResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*
import javax.inject.Inject

@Controller("/api/v1/client/{clientId}")
class QueryPixKeyController(@Inject val grpcClient: KeymanagerQueryDataServiceGrpc.KeymanagerQueryDataServiceBlockingStub) {

    @Get("/pix/{pixId}")
    fun queryPixKeyByClientIdAndPixId(clientId: UUID, pixId: UUID): HttpResponse<Any> {

        val grpcRequest = GrpcQueryPixKeyByClientIdAndPixIdRequest.newBuilder()
            .setPixId(pixId.toString())
            .setClientId(clientId.toString())
            .build()

        val grpcResponse = grpcClient.queryPixKeyByClientIdAndPixId(grpcRequest)

        return HttpResponse.ok(grpcResponse.toQueryDataResponse())

    }

    @Get("/pix/all")
    fun listPixKeyByClientId(clientId: UUID): HttpResponse<Any> {

        val grpcRequest = GrpcListPixKeyByClientIdRequest.newBuilder()
            .setClientId(clientId.toString())
            .build()

        val grpcResponse = grpcClient.listPixKeyByClientId(grpcRequest)

        return HttpResponse.ok(grpcResponse.toListQueryDataResponse())
    }

}