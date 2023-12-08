package com.ad_coding.noteappcourse.ui.screen.note

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ad_coding.noteappcourse.data.local.entity.Audio
import com.ad_coding.noteappcourse.data.local.entity.Fotos
import com.ad_coding.noteappcourse.data.local.entity.FotosCamara
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
                    var ListaF: List<String> = listOf()
                    var ListaC: List<String> = listOf()
                    var Audios: List<String> = listOf()

                    if(note.id!=null)
                    {
                        Log.d("---------------", "LISTAS")
                        var lc : Flow<List<String>>
                        lc = repository.getAllfotosCamara(note.id)
                        Log.d("-LISTA-CAMARA---------", lc.toString())
                        var lf : Flow<List<String>>
                        lf = repository.getAllfotos(note.id)
                        Log.d("-LISTA-FOTO---------", lf.toString())
                        var lA : Flow<List<String>>
                        lA = repository.getAllaudio(note.id)
                        Log.d("-LISTA-AUDIO---------", lA.toString())

                        lf.collect { listLF ->
                            Log.d("LISTFOTOS", listLF.toString())
                            ListaF = listLF
                            lc.collect { listLC ->
                                ListaC = listLC
                                Log.d("OBTENER FOTOSGALE",listLC.toString())
                                  lA.collect { listA ->
                                    Audios = listA
                                      Log.d("OBTENER AUDIOS",listA.toString())
                                      _state.update { screenState ->
                                          screenState.copy(
                                              id = note.id,
                                              title = note.title,
                                              content = note.content,
                                              tipo = note.tipo,
                                              fecha = note.fecha,
                                              foto = note.foto,
                                              fotoS = ListaF,
                                              fotoC = ListaC,
                                              Audios = Audios
                                          )
                                      }
                                }
                            }
                        }

                    }else{
                        repository.updateNote(note)
                    }
                }
            }
        }
    }

    var Urisfotos: List<String> = listOf()
    var UrisCamara: List<String> = listOf()
    var UrisAudio: List<String> = listOf()
    var UrisVideo:  List<String> = listOf()

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.ContentChange -> {
                _state.update {
                    it.copy(
                        content = event.value
                    )
                }
            }
            is NoteEvent.AudioCambio -> {
                Log.d("EVENTO-AUDIOCAMBIO",event.value.toString()+"")
                UrisAudio=event.value
                _state.update {
                    it.copy(
                        Audios = event.value
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
               Urisfotos =event.value
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
            is NoteEvent.FotoCamaraCambio -> {
                UrisCamara = event.value
                _state.update {
                    it.copy(
                        fotoC = event.value
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
                       Log.d("IDNOTANUEVA",id.toString()+"")
                       if (id != null ) {
                        if(Urisfotos!=null)
                        {
                            Urisfotos.forEach{uri->
                                repository.insertFoto(Fotos(0,id,uri))
                                Log.d("SI ENTRO FOTOS",uri)
                            }
                        }
                           Log.d("URISCAMARA",UrisCamara.toString()+"")
                           if(UrisCamara!=null)
                           {
                               UrisCamara.forEach{uri->
                                   repository.insertFotoCamara(FotosCamara(0,id.toLong(),uri))
                                   Log.d("SI ENTRO CAMARA",uri)
                               }
                           }
                           Log.d("URISAUDIO",UrisCamara.toString()+"")
                           if(UrisAudio!=null)
                           {
                               UrisAudio.forEach{uri->
                                   repository.insertAudio(Audio(0,id.toLong(),uri))
                                   Log.d("SI ENTRO AUDIOS",uri)
                               }
                           }
                    }
                   } else {
                       val id = state.id
                       if(Urisfotos!=null)
                       {
                           Urisfotos.forEach{uri->
                               repository.insertFoto(Fotos(0, id.toLong(),uri))
                               Log.d("SI ENTRO FOTOS",uri)
                           }
                       }
                       if(UrisCamara!=null)
                       {
                           UrisCamara.forEach{uri->
                               repository.insertFotoCamara(FotosCamara(0,id.toLong(),uri))
                               Log.d("SI ENTRO CAMARA",uri)
                           }
                       }
                       if(UrisAudio!=null)
                       {
                           UrisCamara.forEach{uri->
                               repository.insertAudio(Audio(0,id.toLong(),uri))
                               Log.d("SI ENTRO AUDIOS",uri)
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
                    if(state.id!=null)
                    {
                        repository.deleteNote(state.id)
                    }
                }
                sendEvent(UiEvent.NavigateBack)
            }

            else -> {}
        }
    }
}