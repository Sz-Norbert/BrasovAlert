package com.nika.brasovalert.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import com.nika.brasovalert.R
import com.nika.brasovalert.databinding.FragmentCreateReportsBinding
import com.nika.brasovalert.databinding.FragmentFavoritesBinding
import com.nika.brasovalert.databinding.FragmentRegisterBinding
import com.nika.brasovalert.mvvm.CreateReportViewModel
import com.nika.brasovalert.mvvm.LoggingViewModel
import com.nika.brasovalert.remote.CreateReportBody
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class CreateReportsFragment:Fragment(R.layout.fragment_create_reports) {

    lateinit var binding: FragmentCreateReportsBinding
    val vm by viewModels<CreateReportViewModel>()
    lateinit var token : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUser()

        binding.imageViewPicker.setOnClickListener {
            pickImg()
        }

        binding.btCreateReport.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val localitate = binding.etOras.text.toString()
            val description = binding.editTextDescription.text.toString()
            val createReportBody = CreateReportBody(token, title, localitate, description)
            vm.postReport(createReportBody)
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

    fun observeUser(){
        vm.userLiveData.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                token = "Bearer ${it.token}"
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