package com.duodevloopers.foodup.Activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.duodevloopers.foodup.Adapter.NoticeAdapter
import com.duodevloopers.foodup.Model.Notice
import com.duodevloopers.foodup.R
import com.duodevloopers.foodup.Utility
import com.duodevloopers.foodup.clicklisteners.NoticeOnClickListener
import com.duodevloopers.foodup.databinding.ActivityNoticeBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.chip.ChipGroup


class NoticeActivity : AppCompatActivity(), ChipGroup.OnCheckedChangeListener,
    AdapterView.OnItemSelectedListener, NoticeOnClickListener {

    private lateinit var binding: ActivityNoticeBinding

    private lateinit var adapter: NoticeAdapter

    private lateinit var selectedSection: String

    private lateinit var selectedType: String

    private val sections = arrayOf("1AM", "1BM", "1CM", "2AM", "2BM", "2CM")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*MyApp.getLoggedInUser()!!.section*/
        selectedSection = ""

        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.chipGroup.setOnCheckedChangeListener(this)
        binding.chipGroup.check(binding.chipGroup.checkedChipId)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, sections
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sectionSpinner.adapter = adapter
        binding.sectionSpinner.onItemSelectedListener = this
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {

        selectedType = when (checkedId) {
            R.id.photo -> "photo"
            R.id.doc -> "doc"
            else -> "notice"
        }

        Utility.showToast(this, selectedType)
        //getNotice(selectedType, selectedSection)

    }

    private fun getNotice(type: String, selectedSection: String) {

        val options = FirestoreRecyclerOptions.Builder<Notice>()
            .setQuery(Utility.getQuery(type, selectedSection), Notice::class.java)
            .build()

        adapter = NoticeAdapter(options, type)
        binding.list.adapter = adapter
        adapter.setNoticeOnClickListener(this)

    }

    override fun onViewPhoto(url: String) {
        TODO("Not yet implemented")
    }

    override fun onViewDoc(url: String) {
        TODO("Not yet implemented")
    }

    override fun onViewNotice(model: Notice) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedSection = sections[position]
        Utility.showToast(this, selectedSection)
        //getNotice(selectedType, selectedSection)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}