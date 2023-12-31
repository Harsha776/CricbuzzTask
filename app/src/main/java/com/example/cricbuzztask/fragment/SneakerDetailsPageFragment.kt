package com.example.cricbuzztask.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cricbuzztask.Model.Sneaker
import com.example.cricbuzztask.R
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.ViewModel.MainViewModel
import com.example.cricbuzztask.ViewModel.MainViewModelFactory
import com.example.cricbuzztask.databinding.FragmentSneakerDetailsPageBinding

/**
 * This class is used for the Selected Sneaker Details
 */
class SneakerDetailsPageFragment : Fragment() {

    private lateinit var fragmentSneakerDetailsPageBinding: FragmentSneakerDetailsPageBinding
    private lateinit var getRepository: GetRepository
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sneakerProduct: Sneaker
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSneakerDetailsPageBinding =
            FragmentSneakerDetailsPageBinding.inflate(inflater, container, false)
        return fragmentSneakerDetailsPageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRepository = GetRepository()
        mainViewModelFactory = MainViewModelFactory(getRepository)
        mainViewModel =
            ViewModelProvider(requireActivity(), mainViewModelFactory)[MainViewModel::class.java]
        mainViewModel.getSneakerDetailsPageData().observe(requireActivity()) {
            sneakerProduct = it
            fragmentSneakerDetailsPageBinding.tvBrand.text = it.brand + " " + it.year
            fragmentSneakerDetailsPageBinding.tvName.text = it.name
            fragmentSneakerDetailsPageBinding.tvPriceVal.text = "$ " + it.retailPrice
        }

        mainViewModel.getCartMessgae().observe(requireActivity()) {
            showToast(it)
        }

        fragmentSneakerDetailsPageBinding.tvSizeEight.isSelected = true
        fragmentSneakerDetailsPageBinding.tvSizeEight.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        fragmentSneakerDetailsPageBinding.ivOrangeCOlor.isSelected = true

        clickListner()
    }

    fun clickListner() {
        fragmentSneakerDetailsPageBinding.tvSizeSeven.setOnClickListener {

            fragmentSneakerDetailsPageBinding.tvSizeSeven.isSelected = true
            fragmentSneakerDetailsPageBinding.tvSizeSeven.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            fragmentSneakerDetailsPageBinding.tvSizeEight.isSelected = false
            fragmentSneakerDetailsPageBinding.tvSizeEight.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            )
            fragmentSneakerDetailsPageBinding.tvSizeNine.isSelected = false
            fragmentSneakerDetailsPageBinding.tvSizeNine.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            )
        }
        fragmentSneakerDetailsPageBinding.tvSizeEight.setOnClickListener {
            fragmentSneakerDetailsPageBinding.tvSizeSeven.isSelected = false
            fragmentSneakerDetailsPageBinding.tvSizeSeven.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            )
            fragmentSneakerDetailsPageBinding.tvSizeEight.isSelected = true
            fragmentSneakerDetailsPageBinding.tvSizeEight.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            fragmentSneakerDetailsPageBinding.tvSizeNine.isSelected = false
            fragmentSneakerDetailsPageBinding.tvSizeNine.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            )
        }
        fragmentSneakerDetailsPageBinding.tvSizeNine.setOnClickListener {
            fragmentSneakerDetailsPageBinding.tvSizeSeven.isSelected = false
            fragmentSneakerDetailsPageBinding.tvSizeSeven.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            )
            fragmentSneakerDetailsPageBinding.tvSizeEight.isSelected = false
            fragmentSneakerDetailsPageBinding.tvSizeEight.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orange
                )
            )
            fragmentSneakerDetailsPageBinding.tvSizeNine.isSelected = true
            fragmentSneakerDetailsPageBinding.tvSizeNine.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }

        fragmentSneakerDetailsPageBinding.ivOrangeCOlor.setOnClickListener {
            fragmentSneakerDetailsPageBinding.ivOrangeCOlor.isSelected = true
            fragmentSneakerDetailsPageBinding.ivPurpleCOlor.isSelected = false
            fragmentSneakerDetailsPageBinding.ivBlueCOlor.isSelected = false
        }

        fragmentSneakerDetailsPageBinding.ivPurpleCOlor.setOnClickListener {
            fragmentSneakerDetailsPageBinding.ivOrangeCOlor.isSelected = false
            fragmentSneakerDetailsPageBinding.ivPurpleCOlor.isSelected = true
            fragmentSneakerDetailsPageBinding.ivBlueCOlor.isSelected = false
        }

        fragmentSneakerDetailsPageBinding.ivBlueCOlor.setOnClickListener {
            fragmentSneakerDetailsPageBinding.ivOrangeCOlor.isSelected = false
            fragmentSneakerDetailsPageBinding.ivPurpleCOlor.isSelected = false
            fragmentSneakerDetailsPageBinding.ivBlueCOlor.isSelected = true
        }

        fragmentSneakerDetailsPageBinding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
            requireActivity().supportFragmentManager.popBackStack()
        }

        fragmentSneakerDetailsPageBinding.tvAddToCart.setOnClickListener {
            mainViewModel.addToCart(sneakerProduct.id)
        }

        fragmentSneakerDetailsPageBinding.ivCheckOut.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_section, CheckoutFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            fragmentSneakerDetailsPageBinding.tvAddToCart.context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}