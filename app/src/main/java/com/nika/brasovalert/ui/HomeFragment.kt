package com.nika.brasovalert.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nika.brasovalert.R
import com.nika.brasovalert.adapter.ReportsAdapter
import com.nika.brasovalert.databinding.FragmentHomeBinding
import com.nika.brasovalert.mvvm.HomefragmentViewModel
import com.nika.brasovalert.remote.DataXXX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    val vm by viewModels<HomefragmentViewModel>()
    lateinit var binding : FragmentHomeBinding
    lateinit var token : String
    private lateinit var reportsAdapter:ReportsAdapter




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createReportsFragment)
        }

        observeUser()
        observeReports()
    }

    private fun observeReports() {


        vm.reortsLiveData.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.tvNoAlert.visibility=View.GONE
                setUpAdapter(it.data)

            }
        })
    }

    fun observeUser(){
        vm.userLiveData.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                token = "Bearer ${it.token}"
                vm.getReports(token)
            }
        })
    }

    fun setUpAdapter(liist : List<DataXXX>){

        reportsAdapter= ReportsAdapter()
        reportsAdapter.setReportList(liist)
        binding.rvReports.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter=reportsAdapter
        }
    }


}