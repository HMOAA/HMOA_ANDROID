package com.hmoa.core_datastore

import com.hmoa.core_database.room.RoomNote
import com.hmoa.core_model.request.NoteResponseDto

fun mapToRoomDBNote(note: NoteResponseDto): RoomNote {
    return RoomNote(
        id = note.noteId,
        content = note.content,
        noteName = note.noteName,
        notePhotoUrl = note.notePhotoUrl
    )
}

fun mapToNote(roomNote: RoomNote): NoteResponseDto {
    return NoteResponseDto(
        noteId = roomNote.id,
        content = roomNote.content,
        noteName = roomNote.noteName,
        notePhotoUrl = roomNote.notePhotoUrl
    )
}