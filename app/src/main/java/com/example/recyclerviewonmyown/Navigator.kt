package com.example.recyclerviewonmyown

import com.example.recyclerviewonmyown.model.User

interface Navigator {

    fun goBack()

    fun showDetails(user: User)

}