package com.example.myapplication

import android.content.Intent
import android.graphics.PointF.length
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

//import com.example.myapplication.databinding.FragmentSecondBinding

class SignUpActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private val emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)


        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        val signUpName : EditText =findViewById(R.id.signUpName)
        val signUpEmail : EditText =findViewById(R.id.signUpEmail)
        val signUpPassword  : EditText =findViewById(R.id.signUpPassword)
        val signUpCPassword : EditText =findViewById(R.id.signUpCPassword)
        val signUpPasswordLayout :TextInputLayout=findViewById(R.id.signUpPasswordLayout)
        val signUpCPasswordLayout :TextInputLayout=findViewById(R.id.signUpCPasswordLayout)
        val signUpBtn: Button =findViewById(R.id.signUpBtn)






        val signinText : TextView =findViewById(R.id.signInText)

        signinText.setOnClickListener{
            val intent= Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
            //setContentView(R.layout.signin_activity)
        }
        signUpBtn.setOnClickListener{
            val name =signUpName.text.toString()
            val email=signUpEmail.text.toString()
            val password= signUpPassword.text.toString()
            val cPassword= signUpCPassword.text.toString()

            signUpCPasswordLayout.isPasswordVisibilityToggleEnabled=true
            signUpPasswordLayout.isPasswordVisibilityToggleEnabled=true

            if(name.isEmpty()|| email.isEmpty()||password.isEmpty()||cPassword.isEmpty()){
                if(name.isEmpty()){
                    signUpName.error="Enter your name"
                }
                if(email.isEmpty()){
                    signUpEmail.error="Enter your Email"
                }
                if(password.isEmpty()){

                    signUpPasswordLayout.isPasswordVisibilityToggleEnabled=false
                    signUpPassword.error="Enter your PASSWORD"
                }
                if(cPassword.isEmpty()){

                    signUpCPasswordLayout.isPasswordVisibilityToggleEnabled=false
                    signUpCPassword.error="confirm your password"
                }
                Toast.makeText(this,"enter valid details",Toast.LENGTH_SHORT).show()

//            }
//            val intent =Intent(this,SignInActivity::class.java)
//            startActivity(intent)

            }else if(!email.matches(emailPattern.toRegex())){
                signUpEmail.error="Enter valid email address"
                Toast.makeText(this,"enter valid email address",Toast.LENGTH_SHORT).show()

            }
            else if((password.length)<6){

                signUpPasswordLayout.isPasswordVisibilityToggleEnabled=false
                signUpPassword.error = "enter password more than 6 characters "
                Toast.makeText(this,"enter password more than 6 characters",Toast.LENGTH_SHORT).show()

            }
            else if(cPassword!=password){

                signUpCPasswordLayout.isPasswordVisibilityToggleEnabled=false
                signUpCPassword.error = "passwords mismatch"
                Toast.makeText(this,"password mismatch",Toast.LENGTH_SHORT).show()


            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val databaseRef =database.reference.child("users").child(auth.currentUser!!.uid)//recheck minute 23.15
                        val users :Users=Users(name,email, auth.currentUser!!.uid)//check min 23:27

                        databaseRef.setValue(users).addOnCompleteListener {
                            if(it.isSuccessful()){

                                val intent= Intent(this, SignInActivity::class.java)
                                startActivity(intent)

                            }
                            else{
                                Toast.makeText(this,"something went wrong , try again",Toast.LENGTH_SHORT).show()

                            }
                        }

                    }
                    else{
                        Toast.makeText(this,"something went wrong , try again",Toast.LENGTH_SHORT).show()

                    }
                }
            }



    }

}

}
/***
/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignUpActivity : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}***/