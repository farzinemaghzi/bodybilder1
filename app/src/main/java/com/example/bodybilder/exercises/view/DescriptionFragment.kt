package com.example.bodybilder.exercises.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bodybilder.R
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.exercises.view.adapter.DescriptionStepAdapter

class DescriptionFragment : Fragment() {

    private lateinit var rvStepImages: RecyclerView  // **fix: RecyclerView**

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // **fix: RecyclerView وصل کن**
        rvStepImages = view.findViewById(R.id.descriptionRecyclerView)  // RecyclerView از layout
        rvStepImages.layoutManager = LinearLayoutManager(requireContext())  // عمودی

        // گرفتن exercise
        val exercise = arguments?.getParcelable<Exercises>("exercise")
        exercise?.let {
            val imageResIds = parseStepImages(it.stepImages)
            val adapter = DescriptionStepAdapter(imageResIds)  // **fix: List<Int> pass – match constructor**
            rvStepImages.adapter = adapter
        } ?: run {
            Toast.makeText(requireContext(), "خطا در لود تمرین", Toast.LENGTH_SHORT).show()
        }
    }

    // **fix: تابع parse String to List<Int> (safe split)**
    private fun parseStepImages(stepImages: String): List<Int> {
        val cleanString = stepImages.replace(Regex("[${'$'}]"), "")  // remove $
        return cleanString.split(",").mapNotNull {
            it.trim().toIntOrNull()  // safe toInt – null ignore
        }
    }
}