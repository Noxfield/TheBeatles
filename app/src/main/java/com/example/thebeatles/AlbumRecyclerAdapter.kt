package com.example.thebeatles

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import java.io.*

class AlbumRecyclerAdapter : RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {

    var albumData = arrayOf<AlbumData>()

    //this is the function that populates the albumdata array
    fun setAlbums(){
        //Get the InputStream associated with albums.txt
        var inStream = MainActivity.getInstance().assets.open("albums.txt")

        //Convert to a BufferedReader
        var reader = BufferedReader(InputStreamReader(inStream))

        //Read all the lines in the file and store in an array:
        var lines = reader.readLines()

        //Convert the List to an array of Lines
        var  arrayLines = lines.toTypedArray()

        //Create a 2D Array
        //var tempData = arrayOf<AlbumData>()

        //Store the fields as a 2-D Array of Strings (Parse the ^ delimited fields)
        //Parse into fields
        for (i in 0..arrayLines.size -1)
        {
            var splitLine = arrayLines[i].split("^")

            albumData +=  AlbumData(splitLine[3],splitLine[0], splitLine[2])
        }

        //Close the Reader
        reader.close()
    }

    //This tells the system how many cells are desired
    override fun getItemCount(): Int {
        return albumData.size
    }

    //This creates a ViewHolder object based on card_layout for each cell
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.album_card_view, parent, false)

        return ViewHolder(v)
    }

    //This initializes the viewHolder to whatever the data source specifies
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.albumTitle.text = albumData.get(position).title
        holder.albumDetail.text = albumData[position].details
        holder.albumImage.setImageResource(
            MainActivity.getInstance().resources.getIdentifier(
                albumData[position].imgName, "drawable",
                MainActivity.getInstance().packageName
            )
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var albumImage: ImageView
        var albumTitle: TextView
        var albumDetail: TextView

        init {
            albumImage = itemView.findViewById(R.id.albumImg)
            albumTitle = itemView.findViewById(R.id.albumTitle)
            albumDetail = itemView.findViewById(R.id.albumData)

            var handler = AlbumHandler()
            itemView.setOnClickListener(handler)
        }

        inner class AlbumHandler() : View.OnClickListener {
            override fun onClick(v: View?) {
                val itemPosition = getLayoutPosition()

                val bundle = Bundle()
                bundle.putString("fileName", albumData.get(itemPosition).imgName)
                v?.findNavController()
                    ?.navigate(R.id.action_albumsFragment_to_songsFragment, bundle)
            }
        }
    }
}
