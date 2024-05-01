package com.example.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Entity
data class Employee (
    @PrimaryKey (autoGenerate = true) val id:Int? = null,
    val name:String, val position:String
)

@Entity(tableName = "customer")
data class Customer(@PrimaryKey (autoGenerate = true) val id:Int? = null,
                    val name:String, val project:String)

@Dao
interface EmployeeDao {
    @Insert
    fun add(employee: Employee)

    @Delete
    fun delete(employee: Employee)

    @Query("SELECT * FROM employee")
    fun getAll(): LiveData<List<Employee>>
}

@Database(entities = [Employee::class, Customer::class], version = 2)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    companion object{
        val MIGRATION_1_2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `customer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL, `project` TEXT NOT NULL)")
            }
        }
    }
}

