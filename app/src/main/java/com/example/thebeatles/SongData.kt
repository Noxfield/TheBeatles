//Matthew Clymer
//App 4 the beatles
//11/2/2020

package com.example.thebeatles

//a class to hold song data. It was giving me errors with the private data fields so i set them to public
class SongData {
    var title = ""
    var details = ""

    constructor(songTitle : String, songDetails : String){
        this.title=songTitle
        this.details=songDetails
    }


}