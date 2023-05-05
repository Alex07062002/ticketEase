package com.ticketEase.backend.DataClasses

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

    object DateSerializer : KSerializer<LocalDateTime> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)
        override fun serialize(encoder: Encoder, value: LocalDateTime) {
            val string = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
            encoder.encodeString(string)
        }

        override fun deserialize(decoder: Decoder): LocalDateTime {
            @Suppress("MagicNumber")
            return LocalDateTime.parse(
                decoder.decodeString().split("[").first().dropLast(6),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
            )
        }
    }
