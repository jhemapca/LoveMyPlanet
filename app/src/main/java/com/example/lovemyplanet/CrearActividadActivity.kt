package com.example.lovemyplanet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.lovemyplanet.models.ActividadesFullModel
import com.example.lovemyplanet.ui.fragments.DatePickerFragment
import com.example.lovemyplanet.ui.fragments.TimePickerFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CrearActividadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_actividad)


        val spnTipo:Spinner=findViewById(R.id.spnTipoAct)
        val txtMaxAforo:EditText=findViewById(R.id.txtMaxPersonasNewAct)
        val txtFecha:EditText=findViewById(R.id.txtFechaNewAct)
        val txtHoraI:EditText=findViewById(R.id.txtHoraIniNewAct)
        val txtHoraF:EditText=findViewById(R.id.txtHoraFinNewAct)
        val txtLugar:EditText=findViewById(R.id.txtLugarNewAct)
        val txtDesc:EditText=findViewById(R.id.txtDescriptionNewAct)
        val btnCrearNuevaActividad:Button=findViewById(R.id.btnSendActividadNueva)
        val db=FirebaseFirestore.getInstance()
        val dbU = FirebaseAuth.getInstance()
        val user: FirebaseUser? = dbU.getCurrentUser()
        val idUser:String = user!!.uid
        var Ausp:String =""

        fun onDateSelected(day: Int, month: Int, year: Int) {
            txtFecha.setText("$day/$month/$year")
        }
        fun showDatePickerDialog() {
            val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
            datePicker.show(supportFragmentManager, "datePicker")
        }
        txtFecha.setOnClickListener{showDatePickerDialog()}

        fun showTimePickerDialog(campo:EditText) {
            fun onTimeSelected(time: String) {
                campo.setText("$time")
            }
            val timePicker = TimePickerFragment { onTimeSelected(it) }
            timePicker.show(supportFragmentManager, "timePicker")
        }
        txtHoraI.setOnClickListener { showTimePickerDialog(txtHoraI) }
        txtHoraF.setOnClickListener { showTimePickerDialog(txtHoraF) }

        var spnTipoValue="Limpieza de Playa"
        spnTipo.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spnTipoValue = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                spnTipoValue = "Limpieza de Playa"
            }

        }

        db.collection("Auspiciadores").document(idUser).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("FIREBASE WARNING", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                Ausp=snapshot.data!!["nombre"].toString()
            }else{
                Log.d("TAG", "Current data: null")
            }
        }
        btnCrearNuevaActividad.setOnClickListener {
            val MaxAforo = txtMaxAforo.text.toString().toInt()
            val Fecha = txtFecha.text.toString()
            val HoraIni=txtHoraI.text.toString()
            val HoraFin=txtHoraF.text.toString()
            val Lugar=txtLugar.text.toString()
            val Desc = txtDesc.text.toString()
            val puntos:Int
            if(spnTipoValue=="Limpieza de Playa"){
                puntos=5
            }else if(spnTipoValue=="Limpieza de Parque"){
                puntos=3
            }else{
                puntos=4
            }
            val id: UUID = UUID.randomUUID()
            val nuevaActi = ActividadesFullModel(spnTipoValue,Fecha, HoraIni, HoraFin, puntos, MaxAforo, Desc, 0, Ausp,Lugar,id.toString())

            db.collection("actividades").document(id.toString()).set(nuevaActi).addOnSuccessListener{
                Toast.makeText(this,"Se ha registrado Nueva Actividad",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainMenu::class.java)
                    startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this,"HA OCURRIDO UN ERROR",Toast.LENGTH_LONG).show()
            }

        }
    }

}