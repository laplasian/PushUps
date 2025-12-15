package database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StatsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statsDao(): StatsDao

    companion object {
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "stats.db"
                ).build()
            }
            return instance!!
        }
    }
}
