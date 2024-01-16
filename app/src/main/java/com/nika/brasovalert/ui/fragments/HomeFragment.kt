package com.nika.brasovalert.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nika.brasovalert.R
import com.nika.brasovalert.adapter.ReportsAdapter
import com.nika.brasovalert.databinding.FragmentHomeBinding
import com.nika.brasovalert.mvvm.HomefragmentViewModel
import com.nika.brasovalert.remote.DataXXX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

   private val vm by viewModels<HomefragmentViewModel>()

  private  lateinit var binding : FragmentHomeBinding
   private lateinit var token : String
    private lateinit var reportsAdapter:ReportsAdapter

    val logged=false



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







        observeEmail()
    }

    private fun observeEmail() {
        vm.emailLivedata.observe(viewLifecycleOwner, Observer {email->
            Toast.makeText(requireContext(), "$email", Toast.LENGTH_SHORT).show()
            observeUser(email)

            binding.floatingActionButton.setOnClickListener {
                val directions=HomeFragmentDirections.actionHomeFragmentToCreateReportsFragment(email)
                findNavController().navigate(directions)


            }
        })


    }


    private fun observeReports() {


        vm.reortsLiveData.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.tvNoAlert.visibility=View.GONE
                setUpAdapter(it.data)

            }
        })
    }

    fun observeUser(email:String){
        vm.getUserDetail(email).observe(viewLifecycleOwner, Observer {
            if (it!=null){
                token = "Bearer ${it.token}"
                vm.getReports(token)
                observeReports()
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