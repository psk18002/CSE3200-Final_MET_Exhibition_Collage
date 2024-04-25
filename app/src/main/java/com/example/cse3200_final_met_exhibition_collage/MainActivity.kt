package com.example.cse3200_final_met_exhibition_collage

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import coil.imageLoader
import coil.load
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cse3200_final_met_exhibition_collage.databinding.ActivityMainBinding
import com.example.cse3200_final_met_exhibition_collage.model.JSON_MetMuseum
import com.google.gson.Gson
import com.example.cse3200_final_met_exhibition_collage.viewModels.UrlViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val gson = Gson()
    private val metPublicDomainUrl = "https://collectionapi.metmuseum.org/public/collection/v1/objects/"
    private var imageData : JSON_MetMuseum? = null
    private lateinit var volleyQueue: RequestQueue


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        volleyQueue = Volley.newRequestQueue(this)

        val uriViewModel: UrlViewModel by viewModels<UrlViewModel>()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextImageButton.setOnClickListener {
            val currentImageID = uriViewModel.getCurrentImageNumber()
            val metUrl = metPublicDomainUrl + currentImageID.toString()
            uriViewModel.setMetaDataUrl(metUrl)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                uriViewModel.getMetaDataUrl(),
                null,
                { response ->
                    imageData = gson.fromJson(response.toString(), JSON_MetMuseum::class.java )
                    uriViewModel.setImageUrl(imageData?.primaryImage.toString() ?: "Foobar")
                    binding.imageView.load(uriViewModel.getImageUrl())

                    uriViewModel.setObjectID(imageData?.objectID.toString())
                    uriViewModel.setTitle(imageData?.title.toString() ?: "Foobar")
                    uriViewModel.setArtist(imageData?.artistDisplayName.toString() ?: "Foobar")
                    uriViewModel.setDate(imageData?.objectDate.toString() ?: "Foobar")
                    binding.textView.setText("Object ID: ${uriViewModel.getObjectID()}\nTitle:\n${uriViewModel.getTitle()}\nArtist: ${uriViewModel.getArtist()}\nDate: ${uriViewModel.getDate()}")
                },
                { error ->  Log.i("PSK" ,"Error: ${error}") })
            volleyQueue.add(jsonObjectRequest)

            uriViewModel.nextImageNumber()
        }
    }
}