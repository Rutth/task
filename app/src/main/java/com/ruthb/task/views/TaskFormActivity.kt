package com.ruthb.task.views

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.ruthb.task.R
import com.ruthb.task.business.PriorityBusiness
import com.ruthb.task.business.TaskBusiness
import com.ruthb.task.constants.TaskConstants
import com.ruthb.task.entities.PriorityEntity
import com.ruthb.task.entities.TaskEntity
import com.ruthb.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    private var mListPriorityEntity: MutableList<PriorityEntity> = mutableListOf()
    private var mListPriorityId: MutableList<Int> = mutableListOf()

    private var mTaskId: Int = 0

    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        loadPriorities()
        setListeners()
        loadDataFromActivity()
    }



    private fun setListeners() {
        btnDate.setOnClickListener(this)
        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnDate -> {
                openDatePickerDialog()
            }
            R.id.btnSave -> {
                handleSave()
            }
        }
    }


    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()

        calendar.set(year, month, dayOfMonth)

        btnDate.text = mSimpleDateFormat.format(calendar.time)

    }

    private fun openDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, dayOfMonth).show()
    }

    private fun handleSave() {
        try {

            val description = edtDescription.text.toString()
            val priorityId = mListPriorityId[spinnerPriority.selectedItemPosition]
            val complete = checkComplete.isChecked
            val dueDate = btnDate.text.toString()
            val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)

            val task = TaskEntity(mTaskId, userId.toInt(), priorityId, description, dueDate, complete)

            if(mTaskId == 0){
                mTaskBusiness.insert(task)
                Toast.makeText(this, getString(R.string.tarefa_cadastrada), Toast.LENGTH_LONG).show()
            } else {
                mTaskBusiness.update(task)
                Toast.makeText(this, getString(R.string.tarefa_alterada), Toast.LENGTH_LONG).show()
            }


            finish()

        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.tente_novamente), Toast.LENGTH_LONG).show()
        }


    }

    private fun getIndex(id: Int): Int{

        var index = 0
        for(i in 0..mListPriorityEntity.size){
            if(mListPriorityEntity[i].id == id){
                index = i
                break
            }
        }
        return index
    }

    private fun loadPriorities() {
        mListPriorityEntity = mPriorityBusiness.getList()

        val listPriori = mListPriorityEntity.map { it.description }
        mListPriorityId = mListPriorityEntity.map { it.id }.toMutableList()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listPriori)

        spinnerPriority.adapter = adapter
    }

    private fun loadDataFromActivity() {
        val bundle = intent.extras
        if(bundle != null){
            mTaskId = bundle.getInt(TaskConstants.BUNDLE.TASKID)

            val task = mTaskBusiness.get(mTaskId)
            if(task != null){
                edtDescription.setText(task.description)
                btnDate.text = task.dueDate
                checkComplete.isChecked = task.complete
                spinnerPriority.setSelection(getIndex(task.priorityId))
            }

        }
    }
}
