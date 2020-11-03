//Matthew Clymer
//App 4 the beatles
//11/2/2020

package com.example.thebeatles

//A class that is used to update the ui to make a psudo slideshow
class Slideshow : Thread {

    //i could have this mostly autopopulated by using the same function from the albumrecycleradapter, but it would be missing the intro1 file, and i did this array first to test the functionality anyways
    private var files : Array<String> = arrayOf("intro1","abbeyroad","beatlesforsale","harddaysnight","help","letitbe","magicalmysterytour","pastmastersvolume1","pastmastersvolume2","pleasepleaseme","revolver",
        "rubber_soul","sgt_pepper","white","with_the_beatles","yellowsubmarine")

    //holds the count position
    var itter = 0

    var running = true

    constructor()
    {

    }


    public override fun run()
    {
        while(running)
        {

            //makes the list loopable
            itter %= files.size

            //create the filename
            var filename = files.get(itter)

            //create the handler to change the image
            var handler = SlideshowHandler(filename)
            MainActivity.getInstance().runOnUiThread(handler)
            try {
                Thread.sleep(3000) //3 sec Delay
            }
            catch (e : InterruptedException){
            }

            itter++
        }
    }

}