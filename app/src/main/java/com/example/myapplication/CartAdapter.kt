package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlin.math.cos


class CartAdapter(private val itemsList:ArrayList<CartItems>):
//class CartAdapter(private val itemsList:ArrayList<CartItems>):


    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    private var firebaseRef= Firebase.database

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.cartlistitems,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=itemsList[position]

        println("fdshugbsd fedsfs " + currentItem.heading)
//        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.itemName.text=currentItem.heading
        holder.itemquantity.text= currentItem.quantity
        holder.itemPrice.text= currentItem.price
        holder.plus.setOnClickListener {

            plusOne( currentItem, holder,currentItem.origPrice,currentItem.MaxQ)
        }
        holder.minus.setOnClickListener {

            minusOne( holder.itemName.text.toString(),
                holder.itemquantity.text.toString(),
                holder.itemPrice.text.toString() , holder,currentItem.origPrice,currentItem.MaxQ)        }
    }



    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        //        if there are more items in the recyclerview/list_items  we need to define these items first in the viewholder
        val titleImage : ShapeableImageView =itemView.findViewById(R.id.title_image)
        val itemName : TextView =itemView.findViewById(R.id.itemName)
        val itemPrice : TextView =itemView.findViewById(R.id.itemprice)
        val itemquantity : TextView =itemView.findViewById(R.id.itemquantity)
        val plus: ImageView =itemView.findViewById(R.id.plus)
        val minus: ImageView=itemView.findViewById(R.id.minus)

    }
    private fun plusOne(item: CartItems , holder: MyViewHolder,origPrice:String,maxQ:String) {

//        var maxquan=0
//        var sellerDatabase = firebaseRef.getReference("Seller")
//        sellerDatabase.child(item.heading).child("quantity")
//        sellerDatabase.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (childSnapshot in snapshot.children) {
//                    Log.d("TESTTTTTTTTT" , Firebase.auth.currentUser?.uid.toString())
//                    if (childSnapshot.child("heading").value.toString().equals(item.heading) ){
//
//                           maxquan=childSnapshot.child("quantity").value.toString().toInt()
//
//                    }
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
        if(item.quantity.toInt()<maxQ.toInt()){

        val orgprc=origPrice
        var qua = (item.quantity.toInt() + 1).toString()
        val cost = (orgprc.toDouble() + item.price.toDouble()).toString()

//        var origPrice=cost.toDouble()/qua.toInt()

        var name=item.heading
        println("fdshugbsd fedsfs " + item.heading)

        val updatedItems = CartItems(name, qua, cost,origPrice,maxQ)
        firebaseRef.getReference("Cart").child(Firebase.auth.currentUser?.uid.toString()).child(item.heading).setValue(updatedItems)
        holder.itemPrice.text = cost
        holder.itemquantity.text = qua
        }

    }
     fun minusOne(Name: String, quantity: String, price: String , holder: MyViewHolder,origPrice: String,maxQ:String) {
         var cost = ""
         var qua = ""
         if(quantity.toInt() >1){
             val orgprc=origPrice
             cost = (price.toDouble()-orgprc.toDouble()).toString()
             qua = (quantity.toInt()-1).toString()
             val updatedItems = CartItems(Name, qua, cost,origPrice,maxQ)
             firebaseRef.getReference("Cart").child(Firebase.auth.currentUser?.uid.toString()).child(Name).setValue(updatedItems)

         }
         else{

             firebaseRef.getReference("Cart").child(Firebase.auth.currentUser?.uid.toString()).child(Name).removeValue()//.setValue(updatedItems)
         }

         holder.itemPrice.text = cost
         holder.itemquantity.text = qua
    }


}