package com.example.cricbuzztask.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricbuzztask.R
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.ViewModel.MainViewModel
import com.example.cricbuzztask.ViewModel.MainViewModelFactory
import com.example.cricbuzztask.adapter.CheckOutAdapter
import com.example.cricbuzztask.adapter.SneakerAdapter
import com.example.cricbuzztask.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment() {
    private lateinit var fragmentCheckoutBinding:FragmentCheckoutBinding
    private lateinit var getRepository: GetRepository
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var checkOutAdapter: CheckOutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentCheckoutBinding= FragmentCheckoutBinding.inflate(inflater,container,false)
        return fragmentCheckoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRepository = GetRepository()
        mainViewModelFactory = MainViewModelFactory(getRepository)
        mainViewModel = ViewModelProvider(requireActivity(),mainViewModelFactory).get(MainViewModel::class.java)
        checkOutAdapter = CheckOutAdapter{id->
            mainViewModel.removeFromCart(id)
        }

        fragmentCheckoutBinding.rvCheckOut.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(requireActivity())
            adapter=checkOutAdapter
        }
        mainViewModel.getCartList().observe(requireActivity(), Observer {
            checkOutAdapter.setData(it)
            var subTotal:Float=0.0f
            for(i in it.indices){
                subTotal+=it.get(i).retailPrice.toInt()
            }
            fragmentCheckoutBinding.tvSubTotal.setText("Subtotal :$ "+subTotal.toString())
            if(subTotal>0) {
                fragmentCheckoutBinding.tvTax.setText("Taxes and Charges: $40")
                fragmentCheckoutBinding.tvPriceVal.setText("$ " + (subTotal + 40))
            }else{
                fragmentCheckoutBinding.tvTax.setText("Taxes and Charges: $0")
                fragmentCheckoutBinding.tvPriceVal.setText("$ " + (subTotal + 0))
            }
        })
        fragmentCheckoutBinding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}