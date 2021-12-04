package com.example.lovemyplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etCorreo: EditText=findViewById(R.id.etCorreoLogin)
        val etContra:EditText=findViewById(R.id.etPasswordLogin)
        val btnCrearCuenta:Button=findViewById(R.id.btnCrearCuentaVol)
        val btnCrearCuentaAusp:Button=findViewById(R.id.btnCrearCuentaAusp)
        val btnLogin:Button=findViewById(R.id.btnIniciarSesion)
        val dbU = FirebaseAuth.getInstance()

        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this,RegistroActivity::class.java)
            startActivity(intent)
        }
        btnCrearCuentaAusp.setOnClickListener {
            val intent = Intent(this,RegistroAuspActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val correo=etCorreo.text.toString()
            val password = etContra.text.toString()
            dbU.signInWithEmailAndPassword(correo,password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "ACCESO PERMITIDO",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this,MainMenu::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "EL USUARIO Y/O CLAVE NO EXISTE EN EL SISTEMA",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}