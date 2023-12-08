package com.ad_coding.noteappcourse.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ad_coding.noteappcourse.data.local.entity.Fotos
import com.ad_coding.noteappcourse.data.local.entity.FotosCamara
import com.ad_coding.noteappcourse.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
//---------------NOTAS------------------------
    @Query("SELECT * FROM NoteEntity")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("""SELECT * FROM NoteEntity WHERE id = :id""")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity):Long

    //@Delete
    @Query("""DELETE  FROM FOTOSCAMARA WHERE id = :id""")
    suspend fun deleteNote(id:Int)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)
    //---------------FOTOS-GALERIA------------------------
    @Insert
    suspend fun insertFoto(foto: Fotos)

    @Query("SELECT Uri FROM Fotos WHERE idnota = :id")
    fun getAllfotos(id: Int): Flow<List<String>>
    //---------------FOTOS-CAMARA------------------------
    @Insert
    suspend fun insertFotoCamara(fotoCamara: FotosCamara)

    @Query("SELECT Uri FROM FotosCamara WHERE idnota = :id")
    fun getAllfotosCamara(id: Int): Flow<List<String>>
}