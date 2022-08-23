package com.example.travelhelperapp.travelPlacesActivity.models

data class Photo(var images: Images)
data class Images(var thumbnail: Thumbnail)
data class Thumbnail(var url: String)