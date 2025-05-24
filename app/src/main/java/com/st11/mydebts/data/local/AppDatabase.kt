package com.st11.mydebts.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.st11.mydebts.model.DebtEntity
import com.st11.mydebts.model.PeopleEntity
import com.st11.mydebts.model.RepayEntity

@Database(entities = [PeopleEntity::class, DebtEntity::class, RepayEntity::class], version = 5, exportSchema = false)
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
