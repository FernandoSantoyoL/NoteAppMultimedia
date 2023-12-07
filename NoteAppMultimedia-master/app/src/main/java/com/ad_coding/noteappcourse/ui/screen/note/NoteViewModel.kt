package com.ad_coding.noteappcourse.ui.screen.note

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ad_coding.noteappcourse.data.local.entity.Fotos
import com.ad_coding.noteappcourse.domain.model.Note
import com.ad_coding.noteappcourse.domain.repository.NoteRepository
import com.ad_coding.noteappcourse.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state = _state.asStateFlow()

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()



    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    init {
        savedStateHandle.get<String>("id")?.let {
            val id = it.toInt()
            viewModelScope.launch {
                repository.getNoteById(id)?.let { note ->
                    var Lista: List<String> = listOf()
                    _state.update { screenState ->
                        screenState.copy(
                            id = note.id,
                            title = note.title,
                            content = note.content,
                            tipo = note.tipo,
                            fecha = note.fecha,
                            foto = note.foto,
                            fotoS = Lista
                        )
                    }
                    Log.d("SE ACTUALIZO","--NOTA--")
                    if(note.id!=null)
              {
                  var l : Flow<List<String>>
                  Log.d("ENTROACTUALIZAR","---"+note.id+"--------")
                  l = repository.getAllfotos(note.id)

                    l.collect { list ->
                      Lista = list
                      Log.d("VALORESLISTA----", "Values: $list")
                        _state.update {
                            it.copy(
                                fotoS = Lista
                            )

                        }
                  }

                  Log.d("SE ACTUALIZO","--------")
              }
                }
            }
        }
    }
    var Uris: List<String> = listOf()
    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.ContentChange -> {
                _state.update {
                    it.copy(
                        content = event.value
                    )
                }
            }
            is NoteEvent.TipoCambio -> {
            _state.update {
                it.copy(
                    tipo = event.value
                )
            }
        }
            is NoteEvent.FotoCambio -> {
               Uris =event.value
                _state.update {
                    it.copy(
                        fotoS = event.value
                    )
                }

            }

            is NoteEvent.FechaCambio -> {
                _state.update {
                    it.copy(
                        fecha = event.value
                    )
                }
            }

            is NoteEvent.TitleChange -> {
                _state.update {
                    it.copy(
                        title = event.value
                    )
                }
            }

            NoteEvent.NavigateBack -> sendEvent(UiEvent.NavigateBack)
            NoteEvent.Save -> {
                viewModelScope.launch {
                    val state = state.value
                    Log.d("", state.id.toString())
                    val note = Note(
                        id = state.id,
                        title = state.title,
                        content = state.content,
                        tipo = state.tipo,
                        fecha= state.fecha,
                        foto = state.foto,
                    )
                    Log.d("INSERTAREDITAR",state.id.toString()+"")
                   if(state.id==null){
                       val id = repository.insertNote(note)
                       if (id != null ) {
                        if(Uris!=null)
                        {
                            Uris.forEach{uri->
                                repository.insertFoto(Fotos(0,id,uri))
                                Log.d("SI ENTRO",uri)
                            }
                        }
                    }
                   } else {
                       val id = state.id
                       if(Uris!=null)
                       {
                           Uris.forEach{uri->
                               repository.insertFoto(Fotos(0, id.toLong(),uri))
                               Log.d("SI ENTRO",uri)
                           }
                       }
                       Log.d("ACTUALIZAR",state.id.toString()+"")
                       repository.updateNote(note)
                    }

                    sendEvent(UiEvent.NavigateBack)
                }
            }

            NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    val state = state.value
                    val note = Note(
                        id = state.id,
                        title = state.title,
                        content = state.content,
                        tipo = state.tipo,
                        fecha= state.fecha,
                        foto = state.foto
                    )
                    repository.deleteNote(note)
                }
                sendEvent(UiEvent.NavigateBack)
            }

            else -> {}
        }
    }
}