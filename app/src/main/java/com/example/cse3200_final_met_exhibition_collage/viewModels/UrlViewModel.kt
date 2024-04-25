package com.example.cse3200_final_met_exhibition_collage.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cse3200_final_met_exhibition_collage.model.ListOfDomainObjectIDs

class UrlViewModel : ViewModel() {

    private var imageUrl: String? = null
    private var metaDataUrl: String? = null
    private var currentImageIndex = MutableLiveData<Int>()

    private var title : String? = null
    private var artist : String? = null
    private var date : String? = null
    private var oid : String? = null

    init {
        currentImageIndex.value = 0
    }

    fun setObjectID(o: String) {
        oid = o
    }
    fun setTitle(t: String) {
        title = t
    }

    fun setArtist(a: String) {
        artist = a
    }

    fun setDate(d: String) {
        date = d
    }

    fun setImageUrl(iurl: String) {
        imageUrl = iurl
    }

    fun setMetaDataUrl(mdurl: String) {
        metaDataUrl = mdurl
    }

    fun getObjectID() : String? {
        return oid
    }

    fun getTitle() : String? {
        return title
    }

    fun getArtist() : String? {
        return artist
    }

    fun getDate() : String? {
        return date
    }

    fun getImageUrl() : String? {
        return imageUrl
    }

    fun getMetaDataUrl() : String? {
        return metaDataUrl
    }

    fun getCurrentImageNumber() : Int {
        return ListOfDomainObjectIDs.getAllMyIds().get(currentImageIndex.value!!).id
    }

    fun nextImageNumber() {
        currentImageIndex.value = currentImageIndex.value?.plus(1)
        currentImageIndex.value = currentImageIndex.value?.rem(ListOfDomainObjectIDs.size())
    }
}
