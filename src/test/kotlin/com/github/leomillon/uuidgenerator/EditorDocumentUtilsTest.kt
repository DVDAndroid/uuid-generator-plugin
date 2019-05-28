package com.github.leomillon.uuidgenerator

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test

private const val UUID = "f2280785-1082-4c8b-aeff-bb852eb98783"
private const val UUID_NO_DASH = "f228078510824c8baeffbb852eb98783"

class EditorDocumentUtilsTest {

    @Test
    fun `should remove dashes from uuid`() {

        // When
        val result = EditorDocumentUtils.toggleUUIDDashes(UUID)

        // Then
        assertThat(result).isEqualTo(UUID_NO_DASH)
    }

    @Test
    fun `should insert dashes into uuid`() {

        // When
        val result = EditorDocumentUtils.toggleUUIDDashes(UUID_NO_DASH)

        // Then
        assertThat(result).isEqualTo(UUID)
    }

    @Test
    fun `should throw invalid format exception for invalid uuid`() {

        assertThat {
            EditorDocumentUtils.toggleUUIDDashes("f2280785 0824c8baeffbb852eb98783")
        }
            .thrownError {
                isInstanceOf(InvalidFormatException::class)
            }
    }

}
