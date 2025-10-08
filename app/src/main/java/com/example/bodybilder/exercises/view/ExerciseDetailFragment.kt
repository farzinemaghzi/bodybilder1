package com.example.bodybilder.exercises.view

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.viewpager2.widget.ViewPager2
import com.example.bodybilder.R
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.exercises.contract.ExercisesContract
import com.example.bodybilder.exercises.presenter.ExercisesPresenter
import com.example.bodybilder.exercises.view.adapter.StepPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseDetailFragment : Fragment(), ExercisesContract.View {
    private lateinit var presenter: ExercisesContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // نمایش BottomNavigationView
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility = View.VISIBLE

        // گرفتن داده‌های تمرین از arguments
        val exercise = arguments?.getParcelable<Exercises>("exercise")
        exercise?.let {
            requireActivity().findViewById<TextView>(R.id.toolbar_title)?.text = it.name
            view.findViewById<TextView>(R.id.exercise_description)?.text = it.description

            // تنظیم عکس پروفایل تمرین
            val imageView = view.findViewById<ImageView>(R.id.exercise_image)
            if (it.imageResId != 0) {
                imageView?.setImageResource(it.imageResId)
            }

            // تنظیم ViewPager2 برای عکس‌های حرکات
            val viewPager = view.findViewById<ViewPager2>(R.id.steps_view_pager)
            val adapter = StepPagerAdapter(it.stepImages)
            viewPager?.adapter = adapter

            // تنظیم TabLayout برای نشانگر اسلایدر
//            val tabLayout = view.findViewById<TabLayout>(R.id.steps_indicator)
//            if (tabLayout != null && viewPager != null) {
//                TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
//            }

            // ساخت presenter
            presenter = ExercisesPresenter(this, requireContext(), null)

            // مدیریت کلیک دکمه add_button
            view.findViewById<ImageButton>(R.id.add_button)?.setOnClickListener {
                val weightsInput = view.findViewById<EditText>(R.id.weights_input)?.text.toString().toIntOrNull()
                val repsInput = view.findViewById<EditText>(R.id.reps_input)?.text.toString().toIntOrNull()

                if (weightsInput == null || repsInput == null || weightsInput <= 0 || repsInput <= 0) {
                    Toast.makeText(requireContext(), "لطفاً تعداد و وزن معتبر وارد کنید", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                presenter.saveWorkoutHistory(
                    exerciseId = it.id,
                    reps = repsInput,
                    weights = weightsInput,
                    date = date
                )
            }
        }

        // اسکرول خودکار به EditText موقع فوکوس
        val nestedScrollView = view as NestedScrollView
        val weightsInput = view.findViewById<EditText>(R.id.weights_input)
        val repsInput = view.findViewById<EditText>(R.id.reps_input)

        weightsInput?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val location = IntArray(2)
                weightsInput.getLocationInWindow(location)
                Log.d("ExerciseDetailFragment", "Weights input focused, top: ${location[1]}")
                Handler(Looper.getMainLooper()).postDelayed({
                    nestedScrollView.smoothScrollTo(0, location[1] - 150)
                }, 300)
            }
        }

        repsInput?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val location = IntArray(2)
                repsInput.getLocationInWindow(location)
                Log.d("ExerciseDetailFragment", "Reps input focused, top: ${location[1]}")
                Handler(Looper.getMainLooper()).postDelayed({
                    nestedScrollView.smoothScrollTo(0, location[1] - 150)
                }, 300)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility = View.VISIBLE
    }

    override fun showExercises(exercises: List<Exercises>) {
        // فعلاً نیازی نیست
    }

    override fun showBodyPartName(name: String) {
        // فعلاً نیازی نیست
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onWorkoutHistorySaved() {
        Toast.makeText(requireContext(), "تمرین ذخیره شد", Toast.LENGTH_SHORT).show()
    }
}