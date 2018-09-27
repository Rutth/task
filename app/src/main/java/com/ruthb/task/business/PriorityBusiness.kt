package com.ruthb.task.business

import android.content.Context
import com.ruthb.task.entities.PriorityEntity
import com.ruthb.task.repository.PriorityRepository

class PriorityBusiness(context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()
}