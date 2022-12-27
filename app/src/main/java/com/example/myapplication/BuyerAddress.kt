package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Math.cos
import java.lang.Math.sin

class BuyerAddress : AppCompatActivity() {
    private lateinit var BranchDatabase: DatabaseReference
    private lateinit var addLocbtn: Button
    private lateinit var lat: EditText
    private lateinit var long: EditText
    private lateinit var auth: FirebaseAuth
    //    UserRef.currentUser?.uid.toString()
    private val Pattern="-?[0-9]+(\\.[0-9]+)?"
    private var UserRef= Firebase.auth
    private var firebaseRef= Firebase.database
    private var distance=0.0
    private var mindistance=1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_address)


        auth=FirebaseAuth.getInstance()
        firebaseRef = FirebaseDatabase.getInstance()

        addLocbtn = findViewById(R.id.nextbutton)
        lat = findViewById(R.id.Blat)
        long = findViewById(R.id.Blong)
        addLocbtn.setOnClickListener {

            val databaseReference= FirebaseDatabase.getInstance().getReference("users")
            if(lat.text!=null&&long.text!=null) {
                if(!lat.text.matches(Pattern.toRegex())&&!long.text.matches(Pattern.toRegex())){
                    Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()

                }
                else {
//                    Toast.makeText(this, "eh daaaaaa", Toast.LENGTH_SHORT).show()

                    val Loc: location = location(
                        lat.text.toString().toDouble(),
                        long.text.toString().toDouble()
                    )//check min 23:27
                    Toast.makeText(this, "el72eeeeneeeeeeee", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, auth.currentUser!!.uid.toString(), Toast.LENGTH_SHORT).show()

                    databaseReference.child(auth.currentUser!!.uid).child("${lat.text.toString().replace('.','D')}+${long.text.toString().replace('.','D')}")
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


            BranchDatabase=FirebaseDatabase.getInstance().getReference("Branches")
            BranchDatabase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    Log.d("ana henaa",BranchDatabase.toString() )
                    for (childSnapshot in snapshot.children) {
//                    if (childSnapshot.child("Seller").value.toString().equals(UserRef.currentUser?.email.toString().replace('.','@'))) {
                        var latitude = childSnapshot.child("lat").value.toString()
                        var longitude = childSnapshot.child("long").value.toString()

                        var longM=(longitude.toDouble() + (-1*long.text.toString().toDouble()))
                        distance= 3963.0 * Math.acos((sin(latitude.toDouble()) * sin(lat.text.toString().toDouble())) + cos(latitude.toDouble()) * cos(lat.text.toString().toDouble()) * cos(longM))

                        if (distance<mindistance){
                            mindistance=distance
                        }
                    }
                    FirebaseDatabase.getInstance().getReference("Cart").child("delivery").child("delivery").setValue((mindistance*1.60934))





                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })



            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }



    }

}