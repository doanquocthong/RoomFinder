package com.example.projectandroidapp_findingroom.viewmodel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    init {
        checkAuthStatus()
    }


    fun checkAuthStatus(){
        if(auth.currentUser==null){
            _authState.value = AuthState.Unauthenticated
        }else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email : String,password : String){
//
//        if(email.isEmpty() || password.isEmpty()){
//            _authState.value = AuthState.Error("Email or password can't be empty")
//            return
//        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    Log.d("Login", "Username: $email, Password: $password, Confirm: $password")
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                    Log.d("Login", "Something wrong!")
                }
            }
    }

    fun signup(email : String,password : String){
        Log.d("Out signup", "Username: $email, Password: $password, Confirm: $password")

//        if(email.isEmpty() || password.isEmpty()){
//            _authState.value = AuthState.Error("Email or password can't be empty")
//            return
//        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    Log.d("Signup", "Username: $email, Password: $password, Confirm: $password")
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                    Log.d("Signup", "Something wrong!")
                }
            }
    }

    fun signout(){
        auth.signOut()
        Log.d("Signout", "Succes signout!")
        _authState.value = AuthState.Unauthenticated
    }


}


sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}