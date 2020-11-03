//Matthew Clymer
//App 4 the beatles
//11/2/2020

package com.example.thebeatles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    var slideshow : Slideshow  = Slideshow()

    companion object
    {
        private var instance : MainFragment? = null
        public fun getInstance() : MainFragment
        {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        slideshow.start()
        return view
    }

    override fun onStop() {
        super.onStop()
        slideshow.running = false
    }

}