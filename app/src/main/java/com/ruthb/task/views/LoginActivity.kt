package com.ruthb.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ruthb.task.R
import com.ruthb.task.business.UserBusiness
import com.ruthb.task.constants.TaskConstants
import com.ruthb.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()

        verifyLoggedUser()
    }

    private fun setListeners() {
        btnLogin.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {
                handleLogin()
            }
            R.id.tvRegister -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    private fun handleLogin() {
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()

        if (mUserBusiness.login(email, password)) {

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } else {
            Toast.makeText(this, getString(R.string.usuario_senha_incorreto), Toast.LENGTH_LONG).show()
        }
    }

    private fun verifyLoggedUser() {
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
        val name = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)

        if (userId != "" && name != "") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}
