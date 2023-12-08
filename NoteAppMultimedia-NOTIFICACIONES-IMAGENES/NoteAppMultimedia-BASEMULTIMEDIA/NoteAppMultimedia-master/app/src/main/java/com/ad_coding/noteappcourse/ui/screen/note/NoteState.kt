package com.ad_coding.noteappcourse.ui.screen.note

import java.util.concurrent.Flow

data class NoteState(
    val id: Int? = null,
    val title: String = "",
    val content: String = "",
    val tipo : String = "",
    val fecha : String = "",
    val foto : String = "",
    val fotoS : List<String> = listOf(),
    val fotoC : List<String> = listOf(),
    val Audios : List<String> = listOf()
)
