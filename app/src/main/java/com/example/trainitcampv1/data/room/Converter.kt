package com.example.trainitcampv1.data.room

import androidx.room.TypeConverter
import com.example.trainitcampv1.data.entity.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}