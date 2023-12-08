package com.ad_coding.noteappcourse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ad_coding.noteappcourse.data.local.dao.NoteDao
import com.ad_coding.noteappcourse.data.local.entity.Audio
import com.ad_coding.noteappcourse.data.local.entity.Fotos
import com.ad_coding.noteappcourse.data.local.entity.FotosCamara
import com.ad_coding.noteappcourse.data.local.entity.NoteEntity
import com.ad_coding.noteappcourse.data.local.entity.Videos

@Database(
    version = 1,
    entities = [NoteEntity::class,Fotos::class,FotosCamara::class,Videos::class,Audio::class]
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val dao: NoteDao

    companion object {
        const val name = "note_db"
    }
}