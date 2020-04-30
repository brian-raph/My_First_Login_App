package com.brianraph.myfirstloginapp

import android.app.ProgressDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.brianraph.myfirstloginapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val mAuth =FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val regBtn = findViewById<View>(R.id.mBtnReg) as Button

        mDatabase =FirebaseDatabase.getInstance().getReference("Names")

      mBtnReg.setOnClickListener(View.OnClickListener{
         view -> register ()
      })
    }

    private fun register(){
        val nameTxt =findViewById<View>(R.id.mEdtName) as EditText
        val emailTxt =findViewById<View>(R.id.mEdtRegEmail) as EditText
        val passwordTxt =findViewById<View>(R.id.mEdtRegPassword) as EditText

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()
        var name = nameTxt.text.toString()
        var progress = ProgressDialog(this)
        progress.setTitle("Registering")
        progress.setMessage("Please wait.......")



        if (!email.isEmpty() && !name.isEmpty() && !password.isEmpty()){
            progress.show()
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                OnCompleteListener {task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        val uid = user!!.uid
                        mDatabase.child("Name").setValue(name)
                        progress.dismiss()
                        Toast.makeText(this,"Successfully signed in",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, Timeline::class.java))
                    } else{
                        progress.dismiss()
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                    }
                })
        }else{
            Toast.makeText(this, "Please fill in all the inputs",Toast.LENGTH_LONG).show()
        }
    }
}
