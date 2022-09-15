package com.hadiyarajesh.easynotes.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.time.Instant

@Entity
@JsonClass(generateAdapter = true)
data class Note(
    @PrimaryKey
    val noteId: Long,
    val title: String,
    val description: String?,
    val contentUrl: List<String>,
    val likesCount: Int,
    val createdOn: Instant,
    val updatedOn: Instant,
)
