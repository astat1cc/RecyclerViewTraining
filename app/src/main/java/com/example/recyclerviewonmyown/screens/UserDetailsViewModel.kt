package com.example.recyclerviewonmyown.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewonmyown.model.User
import com.example.recyclerviewonmyown.model.UsersService

class UserDetailsViewModel(
    private val usersService: UsersService
): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: MutableLiveData<User> = _user

    fun loadUser(userID: Int) {
        val user = usersService.getById(userID)
        _user.value = user
    }

}