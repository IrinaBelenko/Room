package com.example.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReadAndWriteTest {
    private lateinit var database: EmployeeDatabase
    private lateinit var dao: EmployeeDao

    @Before
    fun prepare() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, EmployeeDatabase::class.java).build()
        dao = database.employeeDao()
    }

    @Test
    fun addAndDelete() {
        GlobalScope.launch(Dispatchers.IO) {
        val firstEmployee = Employee(1, "first", "Android developer")
        val secondEmployee = Employee(2, "second", "Android developer")
        dao.add(firstEmployee)
        dao.add(secondEmployee)
        dao.delete(firstEmployee)
        val employeeList = mutableListOf<Employee>()
        dao.getAll().observeForever {
            employeeList.addAll(it)
        }

        Assert.assertEquals(employeeList[0].name, "second")}
    }

}