package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity() {



    lateinit var mapFragment :SupportMapFragment
    lateinit var googleMap: GoogleMap

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val seller : Button =findViewById(R.id.sellerBtn)
        val buyer : Button =findViewById(R.id.buyerBtn)
        val location: Button=findViewById(R.id.locationBtn)

        location.setOnClickListener{

            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        seller.setOnClickListener{

            val intent = Intent(this, SellerActivity::class.java)
            startActivity(intent)
        }

        buyer.setOnClickListener{

            val intent = Intent(this, BuyerActivity::class.java)
            startActivity(intent)
        }

//        auth=FirebaseAuth.getInstance()
//        if(auth.currentUser==null){
//            val intent= Intent(this,SignInActivity::class.java)
//            startActivity(intent)
//        }
    }



}