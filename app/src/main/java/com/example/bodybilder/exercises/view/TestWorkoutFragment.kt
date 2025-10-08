package com.example.bodybilder.exercises.view

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bodybilder.BodyBuilderApp
import com.example.bodybilder.R
import com.example.bodybilder.data.local.AppDatabase
import com.example.bodybilder.data.local.entity.WorkoutHistoryEntity
import com.example.bodybilder.data.repository.WorkoutHistoryRepository
import com.example.bodybilder.exercises.contract.ExerciseDetailContract
import com.example.bodybilder.exercises.presenter.ExerciseDetailPresenter
import com.example.bodybilder.exercises.view.adapter.WorkoutHistoryAdapter

class TestWorkoutFragment : Fragment(), ExerciseDetailContract.view {  // view با v کوچک، طبق Contractت

    private lateinit var presenter: ExerciseDetailPresenter
    private lateinit var adapter: WorkoutHistoryAdapter
    private lateinit var etReps: EditText
    private lateinit var etWeights: EditText
    private lateinit var btnSave: ImageButton
    private lateinit var rvHistory: RecyclerView

    // فرض: exerciseId از navigation یا bundle می‌آد
    private val exerciseId: Int by lazy { arguments?.getInt("exerciseId", 0) ?: 0 }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // وصل viewها
        etReps = view.findViewById(R.id.et_reps)
        etWeights = view.findViewById(R.id.et_weights)
        btnSave = view.findViewById(R.id.btn_save)
        rvHistory = view.findViewById(R.id.rv_history)

        // setup RecyclerView
        adapter = WorkoutHistoryAdapter()
        rvHistory.layoutManager = LinearLayoutManager(context)
        rvHistory.adapter = adapter

        // ساخت Presenter (Repository رو از Database بکش؛ singleton فرض کن)
        val db = (requireActivity().application as BodyBuilderApp).database  // یا AppDatabase.getInstance(requireContext())
        val repository = WorkoutHistoryRepository(db.workoutHistoryDao())
        presenter = ExerciseDetailPresenter(this, repository, exerciseId)

        // listener دکمه save
        btnSave.setOnClickListener {
            val reps = etReps.text.toString().toIntOrNull() ?: 0
            val weights = etWeights.text.toString().toIntOrNull() ?: 0
            if (reps > 0 && weights > 0) {  // validate ساده اضافه کردم تا 0 ذخیره نشه
                presenter.saveWorkout(reps, weights)
                etReps.text.clear()
                etWeights.text.clear()  // پاک کردن input بعد save
            } else {
                showError("تعداد تکرار و وزن باید مثبت باشه!")  // مستقیم از view
            }
        }

        // لود اولیه لیست
        presenter.loadHistory()
    }

    // implement interface view از Contract
    override fun showHistory(list: List<WorkoutHistoryEntity>) {
        adapter.submitList(list)
    }

    override fun showLoading() {
        btnSave.isEnabled = false  // یا ProgressBar نشون بده
    }

    override fun hideLoading() {
        btnSave.isEnabled = true
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }
}