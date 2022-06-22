package com.example.recyclerviewonmyown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewonmyown.databinding.ActivityMainBinding
import com.example.recyclerviewonmyown.model.User
import com.example.recyclerviewonmyown.model.UsersService
import com.example.recyclerviewonmyown.model.UsersListener
import com.example.recyclerviewonmyown.screens.UserDetailsFragment
import com.example.recyclerviewonmyown.screens.UsersListFragment

class MainActivity : AppCompatActivity(), Navigator {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, UsersListFragment())
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun showDetails(user: User) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(user))
            .commit()
    }
}