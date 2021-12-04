package com.example.lovemyplanet

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lovemyplanet.databinding.ActivityMainMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class MainMenu : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val db= FirebaseFirestore.getInstance()


        val navController = findNavController(R.id.nav_host_fragment_activity_main_menu)

        val dbU = FirebaseAuth.getInstance()
        val user: FirebaseUser? = dbU.getCurrentUser()
        val idUser:String = user!!.uid
        var appBarConfiguration = AppBarConfiguration(

            setOf(
                R.id.navPrincipal, R.id.navCalendario, R.id.navTienda,R.id.navPerfil,R.id.navPerfilAusp
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        db.collection("Voluntarios").document(idUser).addSnapshotListener{snapshot, e ->
            if (e != null) {
                Log.w("FIREBASE WARNING", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                navView.menu.findItem(R.id.navPerfilAusp).setVisible(false)
                navView.menu.findItem(R.id.navPerfil).setVisible(true)
            }else{
                navView.menu.findItem(R.id.navPerfil).setVisible(false)
                navView.menu.findItem(R.id.navPerfilAusp).setVisible(true)
            }

        }

    }
}