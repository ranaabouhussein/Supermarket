package com.example.myapplication

import android.content.Intent
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

//import com.example.myapplication.databinding.FragmentFirstBinding

class SignInActivity :AppCompatActivity(){

    private val emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_activity)

        auth= FirebaseAuth.getInstance()

        val signInEmail:EditText=findViewById(R.id.signInEmail)
        val signInPassword:EditText=findViewById(R.id.signInPassword)
        val signInPasswordLayout:TextInputLayout=findViewById(R.id.signInPasswordLayout)
        val signInBtn : Button =findViewById(R.id.signInBtn)
        val signUpText :TextView=findViewById(R.id.signUpText)

        signUpText.setOnClickListener{
            val intent= Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
            //setContentView(R.layout.signup_activity)
        }
        signInBtn.setOnClickListener{
            signInPasswordLayout.isPasswordVisibilityToggleEnabled=true
            val email = signInEmail.text.toString()
            val password=signInPassword.text.toString()

            if(email.isEmpty()||password.isEmpty()){
                if(email.isEmpty()){
                    signInEmail.error="enter email address"
                    signInPasswordLayout.isPasswordVisibilityToggleEnabled=false
                }
                if(password.isEmpty()){
                    signInPassword.error="enter password"
                    signInPasswordLayout.isPasswordVisibilityToggleEnabled=false
                }
                Toast.makeText(this,"enter valid details",Toast.LENGTH_SHORT).show()
            }
            else if(!email.matches(emailPattern.toRegex())){
                signInEmail.error="Enter valid email address"
                Toast.makeText(this,"enter valid email address",Toast.LENGTH_SHORT).show()
            }
            else if((password.length)<6){

                signInPasswordLayout.isPasswordVisibilityToggleEnabled=false
                signInPassword.error = "enter password more than 6 characters "
                Toast.makeText(this,"enter password more than 6 characters",Toast.LENGTH_SHORT).show()

            }
            else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)

                    }else{
                        Toast.makeText(this,"something went wrong , please try again",Toast.LENGTH_SHORT).show()
                    }
                }
            }



    }

}
}
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
/**
class SignInActivity : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
        **/