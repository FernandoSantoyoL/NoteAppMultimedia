package com.ad_coding.noteappcourse.data.repository

import com.ad_coding.noteappcourse.data.local.dao.NoteDao
import com.ad_coding.noteappcourse.data.local.entity.Fotos
import com.ad_coding.noteappcourse.data.local.entity.FotosCamara
import com.ad_coding.noteappcourse.data.mapper.asExternalModel
import com.ad_coding.noteappcourse.data.mapper.toEntity
import com.ad_coding.noteappcourse.domain.model.Note
import com.ad_coding.noteappcourse.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    //---------------NOTAS------------------------
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
            .map { notes ->
                notes.map {
                    it.asExternalModel()
                }
            }
    }
    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.asExternalModel()
    }

    override suspend fun insertNote(note: Note):Long {
        return dao.insertNote(note.toEntity())
    }
    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.toEntity())
    }
    //---------------FOTOS------------------------
    override  fun getAllfotos( id: Int): Flow<List<String>> {
       return dao.getAllfotos(id)

    }
    override suspend fun insertFoto(foto:Fotos) {
        dao.insertFoto(foto)
    }
    //---------------FOTOS-CAMARA-----------------------
    override  fun getAllfotosCamara( id: Int): Flow<List<String>> {
        return dao.getAllfotos(id)

    }
    override suspend fun insertFotoCamara(foto:FotosCamara) {
        dao.insertFotoCamara(foto)
    }
}