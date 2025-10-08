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
import com.example.bodybilder.data.model.BodyPart
import com.example.bodybilder.exercises.contract.BodyPartContract
import com.example.bodybilder.exercises.presenter.BodyPartPresenter
import com.example.bodybilder.exercises.view.adapter.BodyPartsAdapter


class BodyPartFragment : Fragment(), BodyPartContract.View {
    private lateinit var presenter: BodyPartContract.Presenter
    private lateinit var bodyPartsAdapter: BodyPartsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_body_part, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        title.text = "bodyparts"

        presenter = BodyPartPresenter(this, requireContext())

        bodyPartsAdapter = BodyPartsAdapter(emptyList()) { bodyPart ->
            presenter.onBodyPartSelected(bodyPart)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.bodyPartRecyclerView)
        recyclerView.adapter = bodyPartsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        presenter.loadBodyParts()
    }

    override fun showBodyParts(bodyParts: List<BodyPart>) {
        bodyPartsAdapter.updateData(bodyParts)
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}