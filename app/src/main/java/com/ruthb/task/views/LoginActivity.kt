package com.ruthb.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ruthb.task.R
import com.ruthb.task.business.UserBusiness
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)

        setListeners()
    }

    private fun setListeners() {
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {
                handleLogin()
            }
        }
    }

    private fun handleLogin(){
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()

        if(mUserBusiness.login(email, password)){

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } else {
            Toast.makeText(this, getString(R.string.usuario_senha_incorreto), Toast.LENGTH_LONG).show()
        }
    }
}
