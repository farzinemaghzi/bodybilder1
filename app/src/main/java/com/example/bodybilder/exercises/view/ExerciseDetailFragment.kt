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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.bodybilder.R
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.exercises.contract.ExercisesContract
import com.example.bodybilder.exercises.presenter.ExercisesPresenter
import com.example.bodybilder.exercises.view.adapter.StepPagerAdapter
import com.example.bodybilder.exercises.view.adapter.WorkoutHistoryAdapter
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseDetailFragment : Fragment(), ExercisesContract.View {
    private lateinit var presenter: ExercisesContract.Presenter

    // **جدید: property برای Adapter**
    private lateinit var historyAdapter: WorkoutHistoryAdapter

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

        // **جدید: setup RecyclerView (قبل از گرفتن exercise)**
        val rvHistory = view.findViewById<RecyclerView>(R.id.rv_history)
        historyAdapter = WorkoutHistoryAdapter()
        rvHistory.layoutManager = LinearLayoutManager(requireContext())
        rvHistory.adapter = historyAdapter

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


            view.findViewById<ImageButton>(R.id.description)?.setOnClickListener {
                // Bundle بساز و exercise رو pass کن (نه 'it' – 'it' View هست)
                val bundle = Bundle().apply {
                    putParcelable("exercise", exercise)  // **fix: exercise (Parcelable) به جای 'it' (View)**
                }
                val descriptionFragment = DescriptionFragment().apply {
                    arguments = bundle
                }
                // nav به DescriptionFragment
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, descriptionFragment)
                    .addToBackStack("Description")  // نام backStack برای بهتر مدیریت
                    .commit()
            }

            // تنظیم TabLayout برای نشانگر اسلایدر
//            val tabLayout = view.findViewById<TabLayout>(R.id.steps_indicator)
//            if (tabLayout != null && viewPager != null) {
//                TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
//            }

            // ساخت presenter
            presenter = ExercisesPresenter(this, requireContext(), null)

            // **جدید: لود اولیه تاریخچه فقط برای این exerciseId**
            presenter.loadHistoryForExercise(it.id)

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
                    exerciseId = exercise.id,
                    reps = repsInput,
                    weights = weightsInput,
                    date = date
                )
            }
        }
// **اصلاح: اسکرول خودکار + dynamic marginBottom برای کیبورد**
        val nestedScrollView = view as NestedScrollView
        val repsWeightsContainer = view.findViewById<LinearLayout>(R.id.reps_weights_container)  // **جدید: reference به container**
        val weightsInput = view.findViewById<EditText>(R.id.weights_input)
        val repsInput = view.findViewById<EditText>(R.id.reps_input)

        // تابع کمکی برای set marginBottom dynamic (در dp به px)
        fun setContainerMarginBottom(marginDp: Int) {
            val params = repsWeightsContainer.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = (marginDp * resources.displayMetrics.density).toInt()  // **جدید: dp به px**
            repsWeightsContainer.layoutParams = params
            repsWeightsContainer.requestLayout()  // **جدید: force update layout**
        }

        weightsInput?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // **جدید: marginBottom رو به اندازه کیبورد افزایش بده (300dp)**
                setContainerMarginBottom(240)
                val location = IntArray(2)
                weightsInput.getLocationInWindow(location)
                Log.d("ExerciseDetailFragment", "Weights input focused, top: ${location[1]}")
                Handler(Looper.getMainLooper()).postDelayed({
                    nestedScrollView.smoothScrollTo(0, location[1] - 300)  // **تغییر: offset بیشتر (-300dp)**
                }, 150)  // **تغییر: delay کمتر (150ms)**
            } else {
                // **جدید: unfocus: margin رو عادی کن (15dp)**
                setContainerMarginBottom(15)
            }
        }

        repsInput?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // **جدید: marginBottom رو به اندازه کیبورد افزایش بده**
                setContainerMarginBottom(240)
                val location = IntArray(2)
                repsInput.getLocationInWindow(location)
                Log.d("ExerciseDetailFragment", "Reps input focused, top: ${location[1]}")
                Handler(Looper.getMainLooper()).postDelayed({
                    nestedScrollView.smoothScrollTo(0, location[1] - 300)  // **تغییر: offset بیشتر**
                }, 150)  // **تغییر: delay کمتر**
            } else {
                // **جدید: unfocus: margin رو عادی کن**
                setContainerMarginBottom(15)
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
        // **جدید: refresh لیست بعد save**
        val exercise = arguments?.getParcelable<Exercises>("exercise")
        exercise?.let { presenter.loadHistoryForExercise(it.id) }
    }

    // **جدید: implement متد showHistory از Contract**
    override fun showHistory(historyList: List<WorkoutHistoryEntity>) {
        historyAdapter.submitList(historyList)  // آپدیت RecyclerView
    }
}