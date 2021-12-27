package com.duodevloopers.foodup.Activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.duodevloopers.foodup.Adapter.NoticeAdapter
import com.duodevloopers.foodup.Model.Notice
import com.duodevloopers.foodup.R
import com.duodevloopers.foodup.clicklisteners.NoticeOnClickListener
import com.duodevloopers.foodup.databinding.ActivityNoticeBinding
import com.duodevloopers.foodup.myapp.MyApp
import com.duodevloopers.foodup.utility.Utility
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.chip.ChipGroup
import rm.com.longpresspopup.*
import androidx.core.app.ShareCompat





class NoticeActivity : AppCompatActivity(), ChipGroup.OnCheckedChangeListener,
    AdapterView.OnItemSelectedListener, NoticeOnClickListener, View.OnClickListener{

    private val TAG = "NoticeActivity"

    private lateinit var binding: ActivityNoticeBinding

    private lateinit var adapter: NoticeAdapter

    private lateinit var selectedSection: String

    private var selectedType: String = "notice"

    private val sections = arrayOf("1AM", "1BM", "1CM", "2AM", "2BM", "2CM")

    private lateinit var imagePopup: ImageView

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }


    private var clicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (MyApp.loggedInUser?.type  == "student") binding.add.visibility = View.GONE

        binding.add.setOnClickListener(this)
        binding.addFile.setOnClickListener(this)
        binding.addImage.setOnClickListener(this)
        binding.addNotice.setOnClickListener(this)

        /*MyApp.getLoggedInUser()!!.section*/
        selectedSection = MyApp.loggedInUser!!.section


        binding.chipGroup.setOnCheckedChangeListener(this)
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


    override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {

        selectedType = when (checkedId) {
            R.id.photo -> "image"
            R.id.doc -> "doc"
            else -> "notice"
        }

        Utility.showToast(this, selectedType)
        getNotice(selectedType, selectedSection)

    }

    private fun getNotice(type: String, selectedSection: String) {

        val options = FirestoreRecyclerOptions.Builder<Notice>()
            .setQuery(Utility.getQuery(type, selectedSection), Notice::class.java)
            .build()

        adapter = NoticeAdapter(options, type)
        binding.list.layoutManager = GridLayoutManager(this, 2)
        binding.list.adapter = adapter
        adapter.startListening()
        adapter.setNoticeOnClickListener(this)

    }

    override fun onViewPhoto(url: String) {
        val intent = Intent(this, ViewImageActivity::class.java).apply {
            putExtra("url",url)
        }
        startActivity(intent)
    }

    override fun onViewDoc(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.setPackage("com.google.android.apps.docs")
        startActivity(browserIntent)
    }

    override fun onViewNotice(model: Notice) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedSection = sections[position]
        Utility.showToast(this, selectedSection)
        getNotice(selectedType, selectedSection)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.add -> {
                onAddButtonClicked()
            }
            v.id == R.id.add_image -> {
                goToCreateActivity("image")
            }
            v.id == R.id.add_file -> {
                goToCreateActivity("doc")
            }
            v.id == R.id.add_notice -> {
                goToCreateActivity("notice")
            }
        }
    }

    private fun goToCreateActivity(s: String) {

        startActivity(Intent(this, CreateNoticeActivity::class.java).putExtra("type", s))

    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.addNotice.startAnimation(fromBottom)
            binding.addImage.startAnimation(fromBottom)
            binding.addFile.startAnimation(fromBottom)
            binding.add.startAnimation(rotateOpen)
        } else {
            binding.addNotice.startAnimation(toBottom)
            binding.addImage.startAnimation(toBottom)
            binding.addFile.startAnimation(toBottom)
            binding.add.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.addNotice.visibility = View.VISIBLE
            binding.addImage.visibility = View.VISIBLE
            binding.addFile.visibility = View.VISIBLE
        } else {
            binding.addNotice.visibility = View.INVISIBLE
            binding.addImage.visibility = View.INVISIBLE
            binding.addFile.visibility = View.INVISIBLE
        }
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) adapter.stopListening()
    }
}