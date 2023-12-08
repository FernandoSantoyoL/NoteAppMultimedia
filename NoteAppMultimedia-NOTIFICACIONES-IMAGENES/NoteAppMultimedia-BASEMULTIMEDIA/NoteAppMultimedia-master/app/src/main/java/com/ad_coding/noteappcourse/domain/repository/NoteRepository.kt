package com.ad_coding.noteappcourse.domain.repository

import com.ad_coding.noteappcourse.data.local.entity.Fotos
import com.ad_coding.noteappcourse.data.local.entity.FotosCamara
import com.ad_coding.noteappcourse.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    //---------------NOTAS------------------------
    fun getAllNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note):Long

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

    //---------------FOTOS------------------------
    suspend fun insertFoto(fotos: Fotos)

    fun getAllfotos(id: Int): Flow<List<String>>
    //---------------FOTOS-CAMARA-----------------------
    suspend fun insertFotoCamara(fotos: FotosCamara)

    fun getAllfotosCamara(id: Int): Flow<List<String>>
    //--------------------------------------
}