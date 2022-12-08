package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R.id.addbutton
import com.example.myapplication.R.id.cartButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class BuyerActivity : AppCompatActivity() {
    private lateinit var sellerDatabase: DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<BuyerItems>
    lateinit var imageId:Array<Int>
    lateinit var heading:Array<String>
    lateinit var price:Array<Int>
    private var UserRef= Firebase.auth
    private var storageRef= Firebase.storage
    private var firebaseRef= Firebase.database


//    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer)

        firebaseRef= FirebaseDatabase.getInstance()
        storageRef= FirebaseStorage.getInstance()
        newRecyclerView=findViewById(R.id.BrecyclerView)
        newRecyclerView.layoutManager=LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList= arrayListOf<BuyerItems>()
        getItemData()

        val cart : Button =findViewById(cartButton)


        cart.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "cart", Toast.LENGTH_SHORT)
//                                .show()
        }
//        addbutton.set


    }

    private fun getItemData() {
        sellerDatabase=firebaseRef.getReference("Seller")
        sellerDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {

                        var newName = childSnapshot.child("heading").value.toString()
                        var newPrice = childSnapshot.child("price").value.toString()
                        var newQuantity = childSnapshot.child("quantity").value.toString()

//                        val img=storageRef.getReference("images").child(UserRef.currentUser?.uid.toString()).child("$newName.jpeg")

                        val BuyerItem = BuyerItems(newName, newPrice,newQuantity)
                        newArrayList.add(BuyerItem)
                    }


                newRecyclerView.adapter = BuyerAdapter(newArrayList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}