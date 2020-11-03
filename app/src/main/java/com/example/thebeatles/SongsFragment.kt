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
import kotlinx.android.synthetic.main.fragment_songs.*

class SongsFragment : Fragment() {
    companion object {
        private var instance: SongsFragment? = null
        public fun getInstance(): SongsFragment {
            return instance!!
        }
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SongRecyclerAdapter.ViewHolder>? = null
    private var filename = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get the bundle
        var arguments = this.getArguments()
        filename = arguments?.getSerializable("fileName") as String

        layoutManager = LinearLayoutManager(MainActivity.getInstance())
        songs_recycler_view.layoutManager = layoutManager
        adapter = SongRecyclerAdapter()            //we will use an songRecyclerAdapter to autobuild the buttons based off the album's file
        (adapter as SongRecyclerAdapter).setSongs(filename)
        songs_recycler_view.adapter = adapter
    }
}