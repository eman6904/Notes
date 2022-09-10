package com.example.gallery

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gallery.databinding.FragmentImageBinding

class image : Fragment(R.layout.fragment_image) {
    private lateinit var binding: FragmentImageBinding
    private lateinit var navController: NavController

    // //*********to select image from gallery*******
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { binding.imageView.setImageURI(uri) }
        }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    //********************************************************************************************
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageBinding.bind(view)
        navController = Navigation.findNavController(view)
        //to hide action bar
        val activity = activity as MainActivity
        activity.supportActionBar?.hide()
        //to display the image that you select
        selectImageFromGallery()
    }
}