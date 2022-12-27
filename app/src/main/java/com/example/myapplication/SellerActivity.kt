package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class SellerActivity : AppCompatActivity() {
    private lateinit var sellerDatabase: DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<items>
            lateinit var imageList:    ArrayList<String>
//    private lateinit var myAdapter: MyAdapter
    lateinit var imageId:Array<Int>
    lateinit var heading:Array<String>
    lateinit var price:Array<String>
    lateinit var quantity:Array<String>
    private var UserRef= Firebase.auth
    private var storageRef= Firebase.storage
    private var firebaseRef= Firebase.database



//    storageRef.getReference("images").child(UserRef.currentUser?.uid.toString()).child(productName.text.toString())

//val databaseReference=FirebaseDatabase.getInstance().getReference("Seller")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller)


        firebaseRef=FirebaseDatabase.getInstance()
        storageRef= FirebaseStorage.getInstance()
        newRecyclerView=findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager=LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList= arrayListOf<items>()
        getItemData()
        val edit : Button =findViewById(R.id.editButton)
        val loc : Button =findViewById(R.id.locButton)

        edit.setOnClickListener{
            val intent = Intent(this, UploadImage::class.java)
            startActivity(intent)
        }
        loc.setOnClickListener{
            val intent = Intent(this, addLocation::class.java)
            startActivity(intent)
        }

    }


    private fun getItemData() {
        sellerDatabase=firebaseRef.getReference("Seller")
        sellerDatabase.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
//                    if (childSnapshot.child("Seller").value.toString().equals(UserRef.currentUser?.email.toString().replace('.','@'))) {
                        var newName = childSnapshot.child("heading").value.toString()
                        var newPrice = childSnapshot.child("price").value.toString()
                        var newQuantity = childSnapshot.child("quantity").value.toString()

                        val SellerItems= items(newName,newQuantity,newPrice)
                        newArrayList.add(SellerItems)
//                    }

                }
                newRecyclerView.adapter=MyAdapter(newArrayList)


                    }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}



