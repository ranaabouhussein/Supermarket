package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class addLocation : AppCompatActivity() {
    private lateinit var addLocbtn: Button
    private lateinit var lat: EditText
    private lateinit var long: EditText
//    UserRef.currentUser?.uid.toString()
    private val Pattern="-?[0-9]+(\\.[0-9]+)?"
    private var UserRef= Firebase.auth
    private var firebaseRef= Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        firebaseRef = FirebaseDatabase.getInstance()

        addLocbtn = findViewById(R.id.addlocbutton)
        lat = findViewById(R.id.addlatitude)
        long = findViewById(R.id.addlongtuide)
        addLocbtn.setOnClickListener {

            val databaseReference=FirebaseDatabase.getInstance().getReference("Branches")
            if(lat.text!=null&&long.text!=null) {
                if(!lat.text.matches(Pattern.toRegex())&&!long.text.matches(Pattern.toRegex())){
                    Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()

                }
                else {
//                    Toast.makeText(this, "eh daaaaaa", Toast.LENGTH_SHORT).show()

                    val Loc: location = location(
                        lat.text.toString().toDouble(),
                        long.text.toString().toDouble()
                    )//check min 23:27
                    Toast.makeText(this, "el72eeeeneeeeeeee", Toast.LENGTH_SHORT).show()

                    databaseReference.child("${lat.text.toString().replace('.','D')}+${long.text.toString().replace('.','D')}")
                        .setValue(Loc).addOnCompleteListener {

                        if (it.isSuccessful()) {

                            Toast.makeText(this, "Mabrook", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(
                                this,
                                "something went wrong , try again",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }




            }
        }



        }


//            firebaseRef.getReference("Branches").child(lat.text.toString() + "," + long.toString())
//                .child("latitude").setValue(lat.toString()).addOnSuccessListener {
//                Toast.makeText(this, "lat addded", Toast.LENGTH_SHORT)
//                    .show()
//            }
//                .addOnFailureListener { error ->
//                    Toast.makeText(this, "failed to add lat", Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//
//
//            firebaseRef.getReference("Branches").child(lat.text.toString() + "," + long.toString())
//                .child("longitude").setValue(long.toString()).addOnSuccessListener {
//                Toast.makeText(this, "long added", Toast.LENGTH_SHORT)
//                    .show()
//            }
//                .addOnFailureListener { error ->
//                    Toast.makeText(this, "failed to add long", Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//        }
//
//    }

}