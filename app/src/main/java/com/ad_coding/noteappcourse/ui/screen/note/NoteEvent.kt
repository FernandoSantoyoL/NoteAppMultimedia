package com.ad_coding.noteappcourse.ui.screen.note

sealed interface NoteEvent {
    data class TitleChange(val value: String): NoteEvent
    data class ContentChange(val value: String): NoteEvent
    data class TipoCambio(val value: String): NoteEvent
    data class FechaCambio(val value: String): NoteEvent
    data class FotoCambio(val value: List<String>): NoteEvent

    object Save : NoteEvent
    object NavigateBack : NoteEvent
    object DeleteNote : NoteEvent
}