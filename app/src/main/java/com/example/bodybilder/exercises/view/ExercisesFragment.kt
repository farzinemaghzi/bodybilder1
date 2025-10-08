package com.example.bodybilder.exercises.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bodybilder.R
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.data.model.WorkoutHistory
import com.example.bodybilder.exercises.contract.ExercisesContract
import com.example.bodybilder.exercises.presenter.ExercisesPresenter
import com.example.bodybilder.exercises.view.adapter.ExercisesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ExercisesFragment : Fragment(), ExercisesContract.View {
    private lateinit var presenter: ExercisesContract.Presenter
    private lateinit var exercisesAdapter: ExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // گرفتن bodyPartId از arguments
        val bodyPartId = arguments?.getInt("bodyPartId")
        presenter = ExercisesPresenter(this, requireContext(), bodyPartId)

        // تنظیم RecyclerView
        exercisesAdapter = ExercisesAdapter(emptyList()) { exercise ->
            presenter.onExerciseSelected(exercise)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.exercisesRecyclerView)
        recyclerView.adapter = exercisesAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        view.findViewById<FloatingActionButton>(R.id.fab_add_exercise).setOnClickListener {
            val fragment = CustomExerciseFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        // بارگذاری داده‌ها
        presenter.loadExercises()
    }

    override fun showExercises(exercises: List<Exercises>) {
        exercisesAdapter.updateData(exercises)
    }

    override fun showBodyPartName(name: String) {
        val title = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        title?.text = name
        title?.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onWorkoutHistorySaved() {
        // خالی نگه می‌داریم، چون این متد برای ExerciseDetailFragment استفاده می‌شه
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}