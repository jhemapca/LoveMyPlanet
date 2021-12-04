package com.example.lovemyplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.lovemyplanet.models.VoluntarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val dbU = FirebaseAuth.getInstance()
        val db= FirebaseFirestore.getInstance()


        val etCorreo:EditText = findViewById(R.id.etCorreoReg)
        val etContraseña:EditText = findViewById(R.id.etPasswordReg)
        val etDirección:EditText = findViewById(R.id.etDirecciónReg)
        val etBirth:EditText = findViewById(R.id.etBirthDateReg)
        val rgSexo: RadioGroup = findViewById(R.id.gender)
        val etnombres: EditText = findViewById(R.id.etNombresReg)
        val etapellidos:EditText = findViewById(R.id.etApellidosReg)
        val btnReg:Button=findViewById(R.id.btnRegistrar)
        btnReg.setOnClickListener {

            val intSelectedButton: Int = rgSexo!!.checkedRadioButtonId
            val radioButton:RadioButton=findViewById(intSelectedButton)


            val email= etCorreo.text.toString()
            val password = etContraseña.text.toString()

            val nombres=etnombres.text.toString()
            val apellidos=etapellidos.text.toString()
            val direccion=etDirección.text.toString()
            val fechaNac=etBirth.text.toString()
            val genero=radioButton.text.toString()


            if(nombres.length==0){
                etnombres.requestFocus()
                etnombres.setError("Ingrese datos")
            }else if(apellidos.length==0){
                etapellidos.requestFocus()
                etapellidos.setError("Ingrese datos")
            }else if(direccion.length==0){
                etDirección.requestFocus()
                etDirección.setError("Ingrese datos")
            }else if(fechaNac.length==0){
                etBirth.requestFocus()
                etBirth.setError("Ingrese datos")
            }else if(email.length==0){
                etCorreo.requestFocus()
                etCorreo.setError("Ingrese email")
            }else if(password.length>6){
                etContraseña.requestFocus()
                etContraseña.setError("La contraseña debe tener una longitud mayor a 6")
            }else {


                dbU.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    val user: FirebaseUser? = dbU.getCurrentUser()
                    val idUser: String = user!!.uid
                    val datosVoluntario = VoluntarioModel(
                        nombres,
                        apellidos,
                        direccion,
                        fechaNac,
                        genero,
                        email,
                        password,
                        0,
                        false
                    )
                    db.collection("Voluntarios").document(idUser).set(datosVoluntario)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Nuevo Usuario registrado", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener {
                        Toast.makeText(this, "HA OCURRIDO UN ERROR", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

    }
}