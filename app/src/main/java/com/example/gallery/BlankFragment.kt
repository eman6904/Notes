package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
//import com.example.first.databinding.FragmentBlank1Binding
import com.example.gallery.MainActivity
import com.example.gallery.R
import com.example.gallery.databinding.FragmentBlankBinding
import com.mvp.handyopinion.URIPathHelper

class BlankFragment : Fragment(R.layout.fragment_blank) {
    private lateinit var binding:FragmentBlankBinding
    private lateinit var navController: NavController

    // This is ==> how you can Pick Video From Gallery in Kotlin-----------------
    val REQUEST_CODE: Int = 1
    private fun openGalleryForVideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data?.data != null) {
                val uriPathHelper = URIPathHelper()
                val videoFullPath = uriPathHelper.getPath(
                    requireContext(),
                    data?.data!!
                ) // Use this video path according to your logic
                // if you want to play video just after picking it to check is it working
                if (videoFullPath != null) {
                    playVideoInDevicePlayer(videoFullPath)
                }
            }
        }
    }

    fun playVideoInDevicePlayer(videoPath: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoPath))
        intent.setDataAndType(Uri.parse(videoPath), "video/mp4")
        startActivity(intent)
    }

    //------------------------------------------------------------------------------
//another way to select video from gallery----------
//     fun gallery_open()
//     {  val galleryIntent = Intent(Intent.ACTION_PICK)
//         galleryIntent.type="image/*"
//         startActivity(galleryIntent)}
//-----------------------
    //********* to use link **********
    fun link() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://stackoverflow.com/"))
        startActivity(intent)
    }
    //*******************************

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =FragmentBlankBinding.bind(view)
        navController = Navigation.findNavController(view)

        val activity = activity as MainActivity
        activity.supportActionBar?.hide()

        //to open video from gallery
        binding.video.setOnClickListener()
        {
            openGalleryForVideo()
            val intent = Intent()
            onActivityResult(1, 1, intent)
            // gallery_open()
        }
        //to open link------------------
        binding.link.setOnClickListener() {
            link()
        }
        //When you press this button, it will move to the image fragment
        // and choose an image from the gallery and display it there
        binding.image.setOnClickListener()
        { navController.navigate(R.id.action_blankFragment_to_image2) }
    }
}