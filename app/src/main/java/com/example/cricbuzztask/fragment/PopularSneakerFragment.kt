package com.example.cricbuzztask.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cricbuzztask.Model.Sneaker
import com.example.cricbuzztask.R
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.ViewModel.MainViewModel
import com.example.cricbuzztask.ViewModel.MainViewModelFactory
import com.example.cricbuzztask.adapter.SneakerAdapter
import com.example.cricbuzztask.databinding.FragmentPopularSneakerBinding

/**
 * This Class is used to show the Popular Sneaker and showing the search funcationality
 */
class PopularSneakerFragment : Fragment() {

    private lateinit var fragmentPopularSneakerBinding: FragmentPopularSneakerBinding
    private lateinit var getRepository: GetRepository
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sneakerAdapter: SneakerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentPopularSneakerBinding =
            FragmentPopularSneakerBinding.inflate(inflater, container, false)
        return fragmentPopularSneakerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRepository = GetRepository()
        mainViewModelFactory = MainViewModelFactory(getRepository)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        ).get(MainViewModel::class.java)
        sneakerAdapter = SneakerAdapter { id ->
            mainViewModel.setSneakerDetails(id)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_section, SneakerDetailsPageFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        fragmentPopularSneakerBinding.rvPopular.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = sneakerAdapter
        }
        mainViewModel.getSneakerData().observe(requireActivity()) {
            sneakerAdapter.setData(it as ArrayList<Sneaker>)
        }

        mainViewModel.getSearchSneakerData().observe(requireActivity()) {
            sneakerAdapter.setData(it as ArrayList<Sneaker>)
        }

        clickListner()
    }

    /**
     * Click listner events and handling cases
     */
    fun clickListner() {

        fragmentPopularSneakerBinding.tvPopular.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_section, SneakerDetailsPageFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        fragmentPopularSneakerBinding.ivCheckOut.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_section, CheckoutFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        fragmentPopularSneakerBinding.ivSearch.setOnClickListener {
            val dialog = Dialog(requireActivity())

            dialog.setContentView(R.layout.dialog_search)
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)

            val tvOk = dialog.findViewById<View>(com.example.cricbuzztask.R.id.tvOk)
            val tvClose = dialog.findViewById<View>(com.example.cricbuzztask.R.id.tvClose)
            val editText: EditText = dialog.findViewById(R.id.edit_text)

            editText.setText(mainViewModel.searchProduct)
            tvOk.setOnClickListener {
                mainViewModel.searchData(editText.text.toString())
                dialog.dismiss()

            }

            tvClose.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

}