package com.ad_coding.noteappcourse.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey val id: Int?,
    val title: String,
    val content: String,
    val tipo: String,
    val fecha: String,
    val foto  :  String
)

@Entity
data class Fotos(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idNota: Long,
    val Uri: String
)


@Entity
data class FotosCamara(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idNota: Long,
    val Uri: String
)
@Entity
data class Audio(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idNota: Long,
    val Uri: String
)

@Entity
data class Videos(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idNota: Long,
    val Uri: String
)
