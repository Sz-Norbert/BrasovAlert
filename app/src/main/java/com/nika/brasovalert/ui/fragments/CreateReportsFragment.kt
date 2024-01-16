package com.nika.brasovalert.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nika.brasovalert.R
import com.nika.brasovalert.databinding.FragmentCreateReportsBinding
import com.nika.brasovalert.mvvm.CreateReportViewModel
import com.nika.brasovalert.remote.CreateReportBody
import com.nika.brasovalert.repoitory.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class CreateReportsFragment:Fragment(R.layout.fragment_create_reports) {

    lateinit var binding: FragmentCreateReportsBinding
   private val vm by viewModels<CreateReportViewModel>()
    lateinit var token : String
    lateinit var email: String
    private  val args : CreateReportsFragmentArgs by  navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewPicker.setOnClickListener {
            pickImg()
        }

        Toast.makeText(requireContext(), "${args.email}", Toast.LENGTH_SHORT).show()

        args.email?.let {
            email = it
            observeUser(email)
        }

        oncreateRepostPress()



    }

    private fun oncreateRepostPress() {
        binding.btCreateReport.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val localitate = binding.etOras.text.toString()
            val description = binding.editTextDescription.text.toString()
            val createReportBody = CreateReportBody(token, title, localitate.uppercase(), description)
            vm.postReport(createReportBody)
            observePostedReport()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCreateReportsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    fun observeUser(email:String){
        vm.getUserDetail(email).observe(viewLifecycleOwner, Observer {
            if (it!=null){
                token = "Bearer ${it.token}"
            }
        })
    }

    fun observePostedReport(){
        vm.reportResponseLiveData.observe(viewLifecycleOwner, Observer {
            if(it is Resource.Success){

                findNavController().navigate(R.id.action_createReportsFragment_to_homeFragment)

            }
            else{
                binding.etOras.text.clear()
                binding.editTextDescription.text.clear()
                binding.editTextTitle.text.clear()
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun pickImg(){
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(intent)
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { selectedImage ->
                binding.imageViewPicker.setImageURI(selectedImage)

                val inputStream = requireContext().contentResolver.openInputStream(selectedImage)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (bitmap != null) {
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val imageByteArray = stream.toByteArray()
                    Log.d("TAG", "$imageByteArray")
                } else {
                    Log.e("TAG", "Eroare la citirea imaginii")
                }
            }
        }


    }





}