package com.hmoa.core_datastore

import com.hmoa.core_database.room.RoomNote
import com.hmoa.core_model.Note

fun mapToRoomDBNote(note: Note): RoomNote {
    return RoomNote(
        id = note.id,
        content = note.content,
        noteName = note.noteName,
        notePhotoUrl = note.notePhotoUrl
    )
}

fun mapToNote(roomNote: RoomNote): Note {
    return Note(
        id = roomNote.id,
        content = roomNote.content,
        noteName = roomNote.noteName,
        notePhotoUrl = roomNote.notePhotoUrl
    )
}