package com.ruthb.task.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ruthb.task.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setListeners()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnSave -> {
                handlerSave()
            }
        }
    }

    private fun setListeners(){
        btnSave.setOnClickListener(this)
    }

    private fun handlerSave(){

    }
}
