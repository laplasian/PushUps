package database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stats")
data class StatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long,
    val pushups: Int
)
