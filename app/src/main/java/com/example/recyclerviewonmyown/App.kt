package com.example.recyclerviewonmyown

import android.app.Application
import com.example.recyclerviewonmyown.model.UsersService

class App : Application() {
    val usersService = UsersService()
}