package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CartActivity : AppCompatActivity() {
    private lateinit var sellerDatabase: DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<CartItems>
    lateinit var imageId:Array<Int>
    lateinit var heading:Array<String>
    lateinit var price:Array<String>
    lateinit var quantity:Array<String>
    private var UserRef= Firebase.auth
    private var storageRef= Firebase.storage
    private var firebaseRef= Firebase.database
     var total=0.0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)
//        val plus : Button=findViewById(R.id.Plus)
        val checkout : Button =findViewById(R.id.checkoutButton)
        val TotalPrice:TextView=findViewById(R.id.TotalPrice)
        firebaseRef= FirebaseDatabase.getInstance()

//        imageId=arrayOf(R.drawable.apple)
//
//        heading=arrayOf("apple")
//        price= arrayOf(0)
//        quantity= arrayOf(0)

        newRecyclerView=findViewById(R.id.CartrecyclerView)
        newRecyclerView.layoutManager= LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList= arrayListOf<CartItems>()
        getUserdata()


        checkout.setOnClickListener{
            firebaseRef.getReference("Cart").child(Firebase.auth.currentUser?.uid.toString()).removeValue()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "order placed", Toast.LENGTH_SHORT)
                                .show()
        }



            var TotPrice=0.0
//            sellerDatabase=firebaseRef.getReference("Cart").child(Firebase.auth.currentUser?.uid.toString())
            firebaseRef.getReference("Cart").child(Firebase.auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childSnapshot in snapshot.children) {
                        var newPrice = childSnapshot.child("price").value.toString()
                        TotPrice=newPrice.toDouble()+TotPrice
                    }

                    TotalPrice.setText(TotPrice.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


    }

    private fun getUserdata() {
        Log.d("TESTTTTTTTTT" , Firebase.auth.currentUser?.uid.toString())
        sellerDatabase=firebaseRef.getReference("Cart").child(Firebase.auth.currentUser?.uid.toString())
        sellerDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                        var newName = childSnapshot.child("heading").value.toString()
                        var newPrice = childSnapshot.child("price").value.toString()
                        var newQuantity = childSnapshot.child("quantity").value.toString()
                        var orgQ=childSnapshot.child("origPrice").value.toString()
                        var maxQ=childSnapshot.child("maxQ").value.toString()


//                        val img=storageRef.getReference("images").child(UserRef.currentUser?.uid.toString()).child("$newName.jpeg")
                        val BuyerItem = CartItems(newName,newQuantity, newPrice,orgQ,maxQ)
                        newArrayList.add(BuyerItem)
                }
                newRecyclerView.adapter=CartAdapter(newArrayList)

}

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })}
}