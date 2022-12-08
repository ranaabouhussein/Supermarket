package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BuyerAdapter(private val itemsList:ArrayList<BuyerItems> )://,val listener: (clickedItem: BuyerItems) -> Unit):
    RecyclerView.Adapter<BuyerAdapter.MyViewHolder>() {
     var UserRef= Firebase.auth
    private var storageRef= Firebase.storage
    private var firebaseRef= Firebase.database

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.blist_items,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=itemsList[position]
//        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.itemName.text=currentItem.heading
        holder.itemPrice.text= currentItem.price
        holder.addButton.setOnClickListener {
            addItem(
                holder.itemName.text.toString(),
                "1",
                holder.itemPrice.text.toString(),
                currentItem.quantity

            )
        }
//        holder.addButton.setOnClickListener(holder.itemName,holder.itemPrice)
    }



    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        //        if there are more items in the recyclerview/list_items  we need to define these items first in the viewholder
//        val titleImage : ShapeableImageView =itemView.findViewById(R.id.title_image)
        val itemName : TextView =itemView.findViewById(R.id.itemName)
        val itemPrice : TextView =itemView.findViewById(R.id.priceTxt)

        val addButton: Button=itemView.findViewById(R.id.addbutton)
    }
    private fun addItem(Name: String, quantity: String, price: String,maxQ:String) {
        val updatedItems = CartItems(Name, quantity, price,price,maxQ)
        firebaseRef.getReference("Cart").child(Firebase.auth.currentUser?.uid.toString()).child(Name).setValue(updatedItems)
    }
}
