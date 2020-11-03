//Matthew Clymer
//App 4 the beatles
//11/2/2020

package com.example.thebeatles


import android.os.Bundle
import android.provider.Contacts
import android.provider.Contacts.Intents.UI
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class SongRecyclerAdapter : RecyclerView.Adapter<SongRecyclerAdapter.ViewHolder>() {
    var songData = arrayOf<SongData>()

    //this is the function that populates the songdata array
    fun setSongs(filename: String) {
        //Get the InputStream associated with songs.txt
        var inStream = MainActivity.getInstance().assets.open("$filename.txt")

        //Convert to a BufferedReader
        var reader = BufferedReader(InputStreamReader(inStream))

        //Read all the lines in the file and store in an array:
        var lines = reader.readLines()

        //Convert the List to an array of Lines
        var arrayLines = lines.toTypedArray()

        //Create a 2D Array
        //var tempData = arrayOf<songData>()

        //Store the fields as a 2-D Array of Strings (Parse the ^ delimited fields)
        //Parse into fields
        for (i in 0..arrayLines.size - 1) {
            var splitLine = arrayLines[i].split("^")

            songData += SongData(splitLine[0], splitLine[1])
        }

        //Close the Reader
        reader.close()
    }

    //This tells the system how many cells are desired
    override fun getItemCount(): Int {
        return songData.size
    }

    //This creates a ViewHolder object based on card_layout for each cell
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_card_view, parent, false)

        return ViewHolder(v)
    }

    //This initializes the viewHolder to whatever the data source specifies
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.songTitle.text = songData.get(position).title
        holder.songDetail.text = songData[position].details
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var songTitle: TextView
        var songDetail: TextView

        init {
            songTitle = itemView.findViewById(R.id.songTitle)
            songDetail = itemView.findViewById(R.id.songData)

            var handler = songHandler()
            itemView.setOnClickListener(handler)
        }

        inner class songHandler() : View.OnClickListener {
            var bestID = ""
            override fun onClick(v: View?) {
                val itemPosition = getLayoutPosition()

                //SongData(title,details)
                var vidHelp = GetVideoHelper(songData[itemPosition].title,songData[itemPosition].details)

                //because it takes so long to parse the search results, we will run it on another thread
                //after it runs it will call the main thread and navigate to the player fragment with the video playing, if it could find it
                var job = GlobalScope.async {
                    vidHelp.run()
                }
            }
        }
    }
}

class GetVideoHelper : Runnable {

    //size of search sample
    val max = 50

    //holds the id of the best matching video id
    var bestID = "dQw4w9WgXcQ"

    //score of the match with the most points (title having 2 points and artist having 1 points)
    var bestScore = 0

    var artist = ""
    var song =""

    constructor(artist: String,song: String){
        this.artist=artist
        this.song=song
    }

    override fun run() {
        //Set the youtube search, btw when the key goes bad the entire project wiggs out at about the url portion
        var string =
            "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$artist+$song&order=viewCount&maxResults=$max&type=video&videoCategory=Music&key=" //add your own key here from https://console.developers.google.com/apis/library?folder=&organizationId=&project=carbon-poet-259316

        //replace any spaces with +
        string = string.replace(" ", "+")


        val data = URL(string).readText()

        var json = JSONObject(data)
        var items = json.getJSONArray("items") // this is the "items: [ ] part

        var titles = ArrayList<String>()
        var videos = ArrayList<String>()

        for (i in 0 until items.length()) {
            //Get the ith item
            var videoObject = items.getJSONObject(i)

            //Extracth the id Hashmap
            var idDict = videoObject.getJSONObject("id")

            //Get the videoid using videoId key
            var videoId = idDict.getString("videoId")

            //Get the snippet Hashmap
            var snippetDict = videoObject.getJSONObject("snippet")

            //Get the title
            var title = snippetDict.getString("title")

            //Add the titles to the lists
            titles.add(title)
            videos.add(videoId)
        }

        //creates a score for each title/video pair, choosing the highest score for video ID
        for (k in 0 until titles.size) {
            var thisScore = 0

            //adds 2 points to score for having song name in it, i figure its more important to have the song (even if its a cover) than just one of the artists works (or a cover that just mentions them)
            if (titles[k].contains(song, true))
                thisScore += 2

            //adds a point for artist
            if (titles[k].contains(artist, true))
                thisScore += 1

            //if the current score beats the best score, update best ID and score
            if (thisScore > bestScore) {
                bestScore = thisScore
                bestID = videos[k].toString()
            }

        }

        //if the best score isnt better than default, change the bestId to Video+Not+Found
        if (bestScore == 0)
            bestID="Video+Not+Found"

        //runs the navigation on the ui thread
        MainActivity.getInstance().runOnUiThread(){
            val bundle = Bundle()
            bundle.putString("videoID", bestID)
            MainActivity.getInstance().navController.navigate(R.id.action_songsFragment_to_playerFragment, bundle)
        }

    }
}