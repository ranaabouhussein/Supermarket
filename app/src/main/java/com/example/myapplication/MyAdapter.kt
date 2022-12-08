package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyAdapter(private val itemsList: ArrayList<items>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//    private var itemList = mutableListOf<items>()

    private var UserRef= Firebase.auth
    private var storageRef= Firebase.storage
    private var firebaseRef= Firebase.database
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_items,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =itemsList[position]
//        holder.titleImage?.setImageResource(currentItem.titleImage)
//       Glide.with(this).load(storageRef.getReference("images").child(UserRef.currentUser?.uid.toString()).child("${holder.itemName}.jpeg")).into(holder.titleImage)
//        holder.titleImage.setImage(storageRef.getReference("images").child(UserRef.currentUser?.uid.toString()).child("${holder.itemName}.jpeg").downloadUrl)
        holder.itemName.text=currentItem.heading
        holder.itemPrice.text= currentItem.price
        holder.itemQuantity.text= currentItem.quantity

        holder.UPDATE.setOnClickListener {
//            listener(currentItem)
            updateItems(
                holder.itemName.text.toString(),
                holder.itemQuantity.text.toString(),
                holder.itemPrice.text.toString()
                  )
        }


    }
//

    override fun getItemCount(): Int {
       return itemsList.size
    }

//    var titleImage: ImageView?=null


    class MyViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView){

            val itemName :TextView=itemView.findViewById(R.id.itemName)
            val itemPrice:TextView=itemView.findViewById(R.id.Sellerprice)
            val itemQuantity:TextView=itemView.findViewById(R.id.quantity)
            val UPDATE: Button =itemView.findViewById(R.id.UPDATE)


    }
    fun updateItems(Name: String, quantity: String, price: String) {
//        firebaseRef.getReference().child("Seller").child(userId).setValue(user)
        val updatedItems = items(Name, quantity, price)
        firebaseRef.getReference("Seller").child(Name).setValue(updatedItems)
    }
    }