package com.example.cricbuzztask.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricbuzztask.R
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.ViewModel.MainViewModel
import com.example.cricbuzztask.ViewModel.MainViewModelFactory
import com.example.cricbuzztask.adapter.CheckOutAdapter
import com.example.cricbuzztask.databinding.FragmentCheckoutBinding

/**
 * The class is used for the show the cart list product with price details
 */
class CheckoutFragment : Fragment() {
    private lateinit var fragmentCheckoutBinding: FragmentCheckoutBinding
    private lateinit var getRepository: GetRepository
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var checkOutAdapter: CheckOutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCheckoutBinding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return fragmentCheckoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRepository = GetRepository()
        mainViewModelFactory = MainViewModelFactory(getRepository)

        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        ).get(MainViewModel::class.java)
        checkOutAdapter = CheckOutAdapter { id ->
            mainViewModel.removeFromCart(id)
        }

        fragmentCheckoutBinding.rvCheckOut.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = checkOutAdapter
        }

        mainViewModel.getCartList().observe(requireActivity()) {
            checkOutAdapter.setData(it)
            var subTotal = 0.0f
            for (i in it.indices) {
                subTotal += it[i].retailPrice.toInt()
            }
            fragmentCheckoutBinding.tvSubTotal.text = fragmentCheckoutBinding.tvSubTotal.resources.getString(R.string.subTotal) + "" + subTotal
            if (subTotal > 0) {
                fragmentCheckoutBinding.tvTax.text = fragmentCheckoutBinding.tvSubTotal.resources.getString(R.string.tax)
                fragmentCheckoutBinding.tvPriceVal.text = "$ " + (subTotal + 40)
            } else {
                fragmentCheckoutBinding.tvTax.text = fragmentCheckoutBinding.tvSubTotal.resources.getString(R.string.taxWithZero)
                fragmentCheckoutBinding.tvPriceVal.text = "$ " + (subTotal + 0)
            }
        }

        fragmentCheckoutBinding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}