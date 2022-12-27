package com.example.myapplication
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Browser
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
//import com.example.myapplication.databinding.ActivityMainBinding.inflate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

//import com.example.myapplication.databinding.EditActivity.inflate


class UploadImage : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var btnBrowse: Button
    private lateinit var btnUpload: Button
    private lateinit var productName:EditText
    private lateinit var productQuantity: EditText
    private lateinit var productPrice: EditText

    private val Pattern="[0-9]"


    private var UserRef= Firebase.auth
    private var storageRef= Firebase.storage
    private var firebaseRef= Firebase.database


    private lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        storageRef= FirebaseStorage.getInstance()
        firebaseRef = FirebaseDatabase.getInstance()

        productName=findViewById(R.id.UploadName)
        productQuantity=findViewById(R.id.UploadQuantity)
        productPrice=findViewById(R.id.UploadPrice)
        image=findViewById(R.id.firbaseImage)
        btnBrowse=findViewById(R.id.selectImageBtn)
        btnUpload=findViewById(R.id.uploadImageBtn)


        val galleryImage=registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback{
//                val userId=FirebaseAuth.getInstance().currentUser!!.uid
                image.setImageURI(it)
//                image.setImageURI(urll.toString()+"_"+)
                uri=it
//                uri=UserRef
            })

        btnBrowse.setOnClickListener {
            galleryImage.launch("image/*")
        }
        btnUpload.setOnClickListener{

            storageRef.getReference("images").child(UserRef.currentUser?.uid.toString())

                .child(productName.text.toString())
                .putFile(uri)
                .addOnSuccessListener { task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val userId=FirebaseAuth.getInstance().currentUser!!.uid

                            val mapImage=mapOf(
                                "url" to it.toString()
                            )

                            val databaseReference=FirebaseDatabase.getInstance().getReference("Seller")
                            if(productName!=null&&productPrice!=null&&productQuantity!=null) {
//                                databaseReference.child("Image").setValue(mapImage)


                                if(!productPrice.text.matches(Pattern.toRegex())){
                                        Toast.makeText(this,"enter a correct price ",Toast.LENGTH_SHORT).show()
                                    }
                                else if(!productQuantity.text.matches(Pattern.toRegex())){
                                        Toast.makeText(this,"enter a correct quantity ",Toast.LENGTH_SHORT).show()
                                    }
                                else{
                                val products :product=product(productName.text.toString(),productPrice.text.toString(),productQuantity.text.toString())//check min 23:27


//                                Toast.makeText(this, UserRef.currentUser?.email + " SDFSD",Toast.LENGTH_SHORT).show()


//                                databaseReference.child(UserRef.currentUser?.email.toString().replace('.','@')).child(productName.text.toString()).setValue(products).addOnCompleteListener {
                                databaseReference.child(productName.text.toString()).setValue(products).addOnCompleteListener {

                                    if(it.isSuccessful()){

                                        Toast.makeText(this,"Mabrook",Toast.LENGTH_SHORT).show()

                                    }
                                    else{
                                        Toast.makeText(this,"something went wrong , try again",Toast.LENGTH_SHORT).show()

                                    }
                                }
//                                databaseReference.child(userId).child("Image").setValue(mapImage)
//                                    .addOnSuccessListener {
//                                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT)
//                                            .show()
//                                    }
//                                    .addOnFailureListener { error ->
//                                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
//                                            .show()
//                                    }

                                databaseReference.child(productName.text.toString()).child("Seller").setValue(UserRef.currentUser?.email.toString().replace('.','@'))
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    .addOnFailureListener { error ->
                                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
                                            .show()
                                    }
//
//                                databaseReference.child(userId).child("Price").setValue(productPrice.context.toString())
//                                    .addOnSuccessListener {
//                                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT)
//                                            .show()
//                                    }
//                                    .addOnFailureListener { error ->
//                                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
//                                            .show()
//                                    }
//
//                                databaseReference.child(userId).child("Quantity").setValue(productQuantity.context.toString())
//                                    .addOnSuccessListener {
//                                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT)
//                                            .show()
//                                    }
//                                    .addOnFailureListener { error ->
//                                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
//                                            .show()
//                                    }
                            }}
                        }
                }

        }



    }


}