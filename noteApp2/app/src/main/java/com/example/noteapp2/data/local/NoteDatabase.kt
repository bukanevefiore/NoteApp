package com.example.noteapp2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteapp2.data.local.converters.DateConverter
import com.example.noteapp2.data.local.model.Note

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase:RoomDatabase() {
    abstract val noteDao:NoteDao
}