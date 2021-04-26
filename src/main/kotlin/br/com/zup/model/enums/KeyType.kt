package br.com.zup.model.enums

import br.com.caelum.stella.validation.CPFValidator
import br.com.zup.GrpcKeyType
import io.micronaut.validation.validator.constraints.EmailValidator

enum class KeyType(val grpcKeyType: GrpcKeyType) {

    CPF(GrpcKeyType.CPF) {

        override fun isValidKey(keyValue: String?): Boolean {
            if (keyValue.isNullOrBlank()) {
                return false
            }

            return CPFValidator(false)
                .invalidMessagesFor(keyValue)
                .isEmpty()
        }

    },

    TELEFONE_CELULAR(GrpcKeyType.TELEFONE_CELULAR) {
        override fun isValidKey(keyValue: String?): Boolean {

            if (keyValue.isNullOrBlank()) {
                return false
            }
            return keyValue.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },

    EMAIL(GrpcKeyType.EMAIL) {

        override fun isValidKey(keyValue: String?): Boolean {

            if (keyValue.isNullOrBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(keyValue, null)
            }

        }
    },

    CHAVE_ALEATORIA(GrpcKeyType.CHAVE_ALEATORIA) {
        override fun isValidKey(keyValue: String?) = keyValue.isNullOrBlank()
    };

    abstract fun isValidKey(keyValue: String?): Boolean

}