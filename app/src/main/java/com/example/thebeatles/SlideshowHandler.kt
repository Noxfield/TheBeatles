//Matthew Clymer
//App 4 the beatles
//11/2/2020

package com.example.thebeatles
import android.widget.ImageView

//class that changes the image displayed
class SlideshowHandler : Runnable {


    private var fn : String = ""

    constructor(fn : String)
    {
        this.fn = fn
    }

    //this finds the imageview, then changes the image to the filename, fn, from the construtor
    override fun run() {

        var imageView = MainActivity.getInstance().findViewById<ImageView>(R.id.homeImageView)
        var context = imageView.context
        var id = MainActivity.getInstance().resources.getIdentifier(fn, "drawable", context.packageName)
        imageView.setImageResource(id)
    }
}