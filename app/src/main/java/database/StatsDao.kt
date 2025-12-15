package database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StatsDao {

    @Query("SELECT * FROM stats")
    suspend fun getAll(): List<StatsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stat: StatsEntity)
}
