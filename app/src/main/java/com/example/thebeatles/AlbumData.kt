package com.example.thebeatles

//a class to hold album data. It was giving me errors with the private data fields so i set them to public
class AlbumData {
    var imgName = ""
    var title = ""
    var details = ""

    constructor(imageName : String, albumTitle : String, albumDetails : String){
        this.imgName=imageName
        this.title=albumTitle
        this.details=albumDetails
    }
}