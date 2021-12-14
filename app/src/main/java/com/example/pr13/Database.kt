package com.example.pr13

import androidx.room.*

@Entity
data class SupernovaDB (
    @ColumnInfo(name = "name") val name:String
) {
    @PrimaryKey(autoGenerate = true) var uid:Int = 0
}

@Dao
interface SupernovaDAO {
    @Query("SELECT * FROM SupernovaDB")
    fun getAll(): List<SupernovaDB>

    @Insert
    fun insert(supernovae:List<SupernovaDB>)

    @Query("DELETE FROM SupernovaDB")
    fun clear()

}

@Database(entities = arrayOf(SupernovaDB::class),version = 3)
abstract class SupernovaDatabase:RoomDatabase() {
    abstract fun supernovaDao(): SupernovaDAO
}






























