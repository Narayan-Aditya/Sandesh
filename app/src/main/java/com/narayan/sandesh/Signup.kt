package com.narayan.sandesh

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.Date

class signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseDatabase
    private lateinit var selectedImg: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    //For Redirecting to Login Page
        signinbut.setOnClickListener {
            startActivity(Intent(this@signup,signin::class.java))
            finish()
        }

    //For Auth of Firebase
        auth = FirebaseAuth.getInstance()
    //For auth of Database
        database = FirebaseDatabase.getInstance()
    //For Storage
        storage = FirebaseStorage.getInstance()
    //Function for Uploading Img**//
        uploadImg()



    //Function for SignUp User data**//
        ButtonSignup.setOnClickListener {
            val name = Name.text.toString()
            val email = InputEmail.text.toString()
            val password = InputPassword.text.toString()
            val repassword = InputConfirmPassword.text.toString()
            if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()){
                if(password == repassword){

                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this@signup,MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this@signup,"Something Error !",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this@signup,"Password Not Match !",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@signup,"Some fields are empty !",Toast.LENGTH_SHORT).show()
            }
            //Function for Storing user data**//
            uploadUserData()
        }
    }

    private fun uploadUserData() {
        val reference = storage.reference.child("User Data").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener{
            if(it.isSuccessful){
                reference.downloadUrl.addOnCompleteListener { task ->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(UserData: String){
        val user = UserDataModel(Name.text.toString(),InputEmail.text.toString(),InputPassword.text.toString())
        database.reference.child("User")
            .child(Name.text.toString())
            .setValue(user)
    }


    //This is how can upload a img into data base
    private fun uploadImg(){
        ImgProfile.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
            if(data.data != null){
                selectedImg = data.data!!
                ImgProfile.setImageURI(selectedImg)
            }
        }
    }
}