package com.ruthb.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ruthb.task.R
import com.ruthb.task.business.UserBusiness
import com.ruthb.task.repository.UserRepository
import com.ruthb.task.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mUserBusiness = UserBusiness(this)

        setListeners()
        UserRepository.getInstance(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSave -> {
                handlerSave()
            }
        }
    }

    private fun setListeners() {
        btnSave.setOnClickListener(this)
    }

    private fun handlerSave() {
        try {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            mUserBusiness.insert(name, email, password)
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.tente_novamente), Toast.LENGTH_LONG).show()
        }
    }
}
