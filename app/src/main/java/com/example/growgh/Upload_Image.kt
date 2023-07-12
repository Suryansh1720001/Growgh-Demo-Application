package com.example.growgh

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.growgh.databinding.ActivityUploadImageBinding


class Upload_Image : AppCompatActivity() {

    private var binding: ActivityUploadImageBinding? = null

    private var ib_gallery: TextView? = null
    private var imageBackground : ImageView? = null
    private var ic_close : ImageView? = null



    val openGalleryLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == RESULT_OK && result.data!= null){
                imageBackground =findViewById(R.id.iv_upload)
                imageBackground?.setImageURI(result.data?.data)
            }
        }

    private val requestPermission : ActivityResultLauncher<Array<String>> = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permission ->
        permission.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value
            if (isGranted) {
                Toast.makeText(
                    this@Upload_Image,
                    "Permission granted for read storage",
                    Toast.LENGTH_LONG
                ).show()

                val pickIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                openGalleryLauncher.launch(pickIntent)

            } else {
                if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE) {
                    Toast.makeText(this, "Permission denied for read storage", Toast.LENGTH_LONG)
                        .show()
                }

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupActionBar()


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR


        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        ib_gallery = findViewById(R.id.btnSelect)
                ib_gallery?.setOnClickListener {
                    requestStoragePermission()
                }

        ic_close = findViewById(R.id.iv_close)
        ic_close?.setOnClickListener{
            imageBackground?.setImageResource(R.drawable.baseline_insert_photo_24);

        }


    }


    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarUpload)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.setTitle(0)
        }
        binding?.toolbarUpload?.setNavigationOnClickListener {
            onBackPressed()
        }
    }



    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,Manifest.permission.READ_EXTERNAL_STORAGE
            )){
            showRationalDialog("Growgh", "Grown Internship app"+"needs to Access your External Storage")
        }else{
            requestPermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))

            // TODO - Add writing external storage permission
        }
    }




    private fun showRationalDialog(
        title: String,
        message: String,
    ){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
        builder.create().show()
    }

}



