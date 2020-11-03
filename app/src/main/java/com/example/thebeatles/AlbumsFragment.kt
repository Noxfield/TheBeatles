//Matthew Clymer
//App 4 the beatles
//11/2/2020

package com.example.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_albums.*


class AlbumsFragment : Fragment() {
    companion object {
        private var instance: AlbumsFragment? = null
        public fun getInstance(): AlbumsFragment {
            return instance!!
        }
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(MainActivity.getInstance())
        album_recycler_view.layoutManager = layoutManager
        adapter = AlbumRecyclerAdapter()            //we will use an albumRecyclerAdapter to autobuild the buttons based off the albums.txt file
        (adapter as AlbumRecyclerAdapter).setAlbums()
        album_recycler_view.adapter = adapter
    }
}