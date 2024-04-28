package com.dicoding.asclepius.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null

//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if(isGranted) {
//            Toast.makeText(this, "permission is granted", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this, "permission request denied", Toast.LENGTH_LONG).show()
//        }
//    }

//    private fun allPermissionIsGranted() =
//        ContextCompat.checkSelfPermission(
//            this,
//            REQUIRED_PERMISSION
//        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if(!allPermissionIsGranted()){
//            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
//        }

        binding.galleryButton.setOnClickListener { startGallery()}
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let {
                analyzeImage(it)
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }

    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }


    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri?.let {
            Log.d("image URI", " showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

//    private fun moveToResult() {
//        val intent = Intent(this, ResultActivity::class.java)
//        startActivity(intent)
//    }
    private fun analyzeImage(uri: Uri) {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, uri.toString())
        startActivity(intent)
    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }


}