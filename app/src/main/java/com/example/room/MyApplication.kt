package com.example.room

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    lateinit var repo: EmployeeRepository
    override fun onCreate() {
        super.onCreate()
        instance = this
        val db = Room.databaseBuilder(this, EmployeeDatabase::class.java, "employee_database")
            .addMigrations(EmployeeDatabase.MIGRATION_1_2)
            .build()
        repo = EmployeeRepository(db)
    }
    companion object {
        private lateinit var instance: MyApplication
        fun getApp() = instance
    }
}