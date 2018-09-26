package com.ruthb.task.business

import android.content.Context
import com.ruthb.task.R
import com.ruthb.task.constants.DatabaseConstants
import com.ruthb.task.constants.TaskConstants
import com.ruthb.task.entities.UserEntity
import com.ruthb.task.repository.UserRepository
import com.ruthb.task.util.SecurityPreferences
import com.ruthb.task.util.ValidationException

class UserBusiness(var context: Context) {
    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login(email: String, password: String): Boolean{
        val user: UserEntity? = mUserRepository.get(email, password)

        return if(user != null){
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)

            true
        } else {
            false
        }
    }

    fun insert(name: String, email: String, password: String){
        try {
            if(name == "" || email == "" || password == ""){
                throw ValidationException(context.getString(R.string.informe_campos))
            }

            if(mUserRepository.isEmailExistent(email)){
                throw ValidationException(context.getString(R.string.email_existente))
            }
                
            val userId = mUserRepository.insert(name, email, password)

            //salvando dados
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)

            
        } catch (e: Exception){
            throw e
        }

    }
}