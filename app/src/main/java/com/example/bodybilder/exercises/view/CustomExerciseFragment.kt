package com.example.bodybilder.exercises.view

import android.net.Uri
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bodybilder.R
import com.example.bodybilder.data.local.entity.BodyPartEntity
import com.example.bodybilder.data.model.Exercises
import com.example.bodybilder.databinding.ActivityMainBinding
import com.example.bodybilder.exercises.contract.CustomExerciseContract
import com.example.bodybilder.exercises.contract.CustomExercisePresenter
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class CustomExerciseFragment : Fragment(), CustomExerciseContract.View {
    lateinit var binding: ActivityMainBinding
    private lateinit var presenter: CustomExerciseContract.Presenter
    val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){
        val galleryUri = it
        try {
            val imageView = view?.findViewById<android.widget.ImageView>(R.id.iv_exercise_image)
            imageView?.setImageURI(galleryUri)
        }
        catch (e: Exception){
            Toast.makeText(this.context, "خطا در بارگذاری تصویر", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val name = view.findViewById<TextInputEditText>(R.id.et_exercise_name).text.toString()
        val reps = view.findViewById<TextInputEditText>(R.id.et_reps).text.toString().toIntOrNull() ?: 0
        val sets = view.findViewById<TextInputEditText>(R.id.et_sets).text.toString().toIntOrNull() ?: 0
        val discription = view.findViewById<TextInputEditText>(R.id.et_description).text.toString()
        val btnSelectImage = view.findViewById<Button>(R.id.btn_select_image)

        btnSelectImage.setOnClickListener {
            galleryLauncher.launch("image/*")

        }


        val exercises = Exercises (
            id = 0,
            name = name,
            reps = reps,
            sets = sets,
            description = discription,
            imageResId = 0,
            stepImages = emptyList(),
            isCustom = true,
        )





    }

    override fun showBodyParts(bodyParts: List<BodyPartEntity>) {
        val spinner = view?.findViewById<Spinner>(R.id.spinner_body_part)
        spinner?.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            bodyParts
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    override fun showSuccess() {
        Toast.makeText(context, "تمرین با موفقیت ذخیره شد", Toast.LENGTH_SHORT).show()
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}