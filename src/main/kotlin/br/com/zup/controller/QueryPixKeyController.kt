package br.com.zup.controller

import br.com.zup.GrpcQueryPixKeyByClientIdAndPixIdRequest
import br.com.zup.KeymanagerQueryDataServiceGrpc
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

}