package com.example.travelhelperapp.travelPlacesActivity.models

data class Photo(var images: Images, var id: String)
data class Images(var thumbnail: Thumbnail, var large: Large)
data class Large(var url: String)
data class Thumbnail(var url: String)