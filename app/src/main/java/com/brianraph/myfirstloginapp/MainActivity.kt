package com.brianraph.myfirstloginapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.brianraph.myfirstloginapp.R
import com.brianraph.myfirstloginapp.RegisterActivity
import com.brianraph.myfirstloginapp.Timeline
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var BtnLogin =findViewById<View>(R.id.mBtnLogin) as Button
        val BtnReg =findViewById<View>(R.id.mBtnReg) as Button


        mBtnLogin.setOnClickListener(View.OnClickListener {
            view -> login()
        })
        BtnReg.setOnClickListener(View.OnClickListener {
                view ->  register()
        })

    }
    private fun login() {
        val emailTxt =findViewById<View>(R.id.mEdtEmail) as EditText
        val passwordTxt =findViewById<View>(R.id.mEdtPassword) as EditText

        var email = emailTxt.text.toString()
        var password =passwordTxt.text.toString()
        var progress = ProgressDialog(this)
        progress.setTitle("Login")
        progress.setMessage("Please wait.......")


        if (!email.isEmpty() && !password.isEmpty()){
            progress.show()
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,
                OnCompleteListener { task ->
                    if(task.isSuccessful){
                        startActivity(Intent(this, Timeline::class.java))
                        progress.dismiss()
                        clear()
                        Toast.makeText(this,"Successfully logged in",Toast.LENGTH_LONG).show()
                    }else{
                        progress.dismiss()
                       Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                    }
                })
        }else{
            Toast.makeText(this, "Please fill in all the inputs",Toast.LENGTH_LONG).show()
        }
    }

    private fun register(){
        var progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait.......")
        progress.show()
       startActivity(Intent(this,RegisterActivity::class.java))
        clear()
        progress.dismiss()
    }
    private fun clear(){
        mEdtEmail.setText("")
        mEdtPassword.setText("")
    }
}
