package com.ospaltic.mydebts.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ospaltic.mydebts.model.DebtEntity
import com.ospaltic.mydebts.model.PeopleEntity
import com.ospaltic.mydebts.model.RepayEntity

@Database(entities = [PeopleEntity::class, DebtEntity::class, RepayEntity::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun peopleDao(): PeopleDao
    abstract fun debtDao(): DebtDao
    abstract fun debtRepayDao(): DebtRepayDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "debt_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
