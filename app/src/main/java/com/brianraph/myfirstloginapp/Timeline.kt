package com.brianraph.myfirstloginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.brianraph.myfirstloginapp.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text

class Timeline : AppCompatActivity() {

    val mAuth =FirebaseAuth.getInstance()
    lateinit var mDatabase :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        val dispTxt =findViewById<View>(R.id.mTxtDisplay) as TextView

        mDatabase =FirebaseDatabase.getInstance().getReference("Names")

        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val result:String = snapshot.child("Name").toString()
                dispTxt.text="Welcome "+result
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu ,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item!!.itemId == R.id.logout){
            mAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this,"Logged out", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
