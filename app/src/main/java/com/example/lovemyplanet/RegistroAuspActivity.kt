package com.example.lovemyplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lovemyplanet.models.AuspiciadorModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegistroAuspActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_ausp)

        val dbU = FirebaseAuth.getInstance()
        val db= FirebaseFirestore.getInstance()

        val etNombreEmp:EditText=findViewById(R.id.etNombAuspReg)
        val etNombreEncargado:EditText=findViewById(R.id.etNombEncargadoAuspReg)
        val etTelefono:EditText=findViewById(R.id.etTelefonoAuspReg)
        val etDireccion:EditText=findViewById(R.id.etDireccionAuspReg)
        val etCorreo:EditText=findViewById(R.id.etCorreoAuspReg)
        val etContra:EditText=findViewById(R.id.etPasswordAuspReg)
        val btnReg:Button=findViewById(R.id.btnRegistrarAusp)

        btnReg.setOnClickListener {
            if(etNombreEmp.text.toString().isEmpty()){
                etNombreEmp.requestFocus()
                etNombreEmp.setError("Ingrese nombre de empresa")
            }else if(etNombreEncargado.text.toString().isEmpty()){
                etNombreEncargado.requestFocus()
                etNombreEncargado.setError("Ingrese nombre de encargado")
            }else if(etTelefono.text.toString().isEmpty()){
                etTelefono.requestFocus()
                etTelefono.setError("Ingrese numero telefonico")
            }else if(etDireccion.text.toString().isEmpty()){
                etDireccion.requestFocus()
                etDireccion.setError("Ingrese una dirección")
            }else if(etCorreo.text.toString().isEmpty()){
                etCorreo.requestFocus()
                etCorreo.setError("Ingrese una dirección email")
            }else if(etContra.text.toString().length<6){
                etContra.requestFocus()
                etContra.setError("La contraseña debe tener más de 6 caracteres")
            }else{
                val email= etCorreo.text.toString()
                val password = etContra.text.toString()

                val NombreEmpresa = etNombreEmp.text.toString()
                val Encargado = etNombreEncargado.text.toString()
                val Telefono = etTelefono.text.toString()
                val Direccion = etDireccion.text.toString()

                dbU.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    val user: FirebaseUser? = dbU.getCurrentUser()
                    val idUser:String = user!!.uid
                    val datosAusp=AuspiciadorModel(NombreEmpresa, Encargado, Telefono, Direccion, email, password, 20, true)
                    db.collection("Auspiciadores").document(idUser).set(datosAusp).addOnSuccessListener {
                        Toast.makeText(this,"Nuevo Auspiciador registrado", Toast.LENGTH_LONG).show()
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                    }.addOnFailureListener{
                        Toast.makeText(this,"HA OCURRIDO UN ERROR",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}