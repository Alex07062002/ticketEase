package com.ticketEase.backend.DataClasses

import kotlinx.serialization.Serializable
import java.time.Instant


@Serializable
data class Catalog(val name : String,
                   val price : Double,
                   val location : String,
                   @Serializable(with = DateSerializer::class)
                   val date : Instant)
