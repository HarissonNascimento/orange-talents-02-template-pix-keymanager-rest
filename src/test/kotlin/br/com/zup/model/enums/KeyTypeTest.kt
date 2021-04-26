package br.com.zup.model.enums

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class KeyTypeTest {

    @Nested
    inner class CPF {

        @Test
        fun `must be valid when CPF is valid`() {
            with(KeyType.CPF) {
                assertTrue(isValidKey("02467781054"))
            }
        }

        @Test
        fun `it should not be valid when CPF is invalid`() {
            with(KeyType.CPF) {
                assertFalse(isValidKey("invalid cpf"))
            }
        }

    }

    @Nested
    inner class PHONE {

        @Test
        fun `must be valid when phone number format is valid`() {
            with(KeyType.TELEFONE_CELULAR) {
                assertTrue(isValidKey("+5511111111111"))
            }
        }

        @Test
        fun `it should not be valid when phone number format is invalid`() {
            with(KeyType.TELEFONE_CELULAR) {
                assertFalse(isValidKey("invalid phone number"))
            }
        }

    }

    @Nested
    inner class EMAIL {

        @Test
        fun `must be valid when email format is valid`() {
            with(KeyType.EMAIL) {
                assertTrue(isValidKey("email@test.com"))
            }
        }

        @Test
        fun `it should not be valid when email format is invalid`() {
            with(KeyType.EMAIL) {
                assertFalse(isValidKey("invalid email format"))
            }
        }

    }

    @Nested
    inner class RANDOM {

        @Test
        fun `must be valid when random key is null or blank`() {
            with(KeyType.CHAVE_ALEATORIA) {
                assertTrue(isValidKey(null))
                assertTrue(isValidKey(""))
            }
        }

        @Test
        fun `it should not be valid when random key has a value`() {
            with(KeyType.CHAVE_ALEATORIA) {
                assertFalse(isValidKey("random value"))
            }
        }

    }


}