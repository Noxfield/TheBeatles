//Matthew Clymer
//App 4 the beatles
//11/2/2020

package com.example.thebeatles

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import kotlinx.android.synthetic.main.fragment_player.view.*

class PlayerFragment : Fragment() {

    private var videoID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get the bundle
        var arguments = this.getArguments()
        videoID = arguments?.getSerializable("videoID") as String

        //set the webview to invisible and show the error message if the video wasnt found
        if (videoID == "Video+Not+Found") {
            var errorTxt = view.textViewVideoError
            var video = view.webViewObject

            //switch the visibilities
            errorTxt.visibility=View.VISIBLE
            video.visibility=View.INVISIBLE

        } else {
            var webDelegate = Delegate()
            view.webViewObject.webViewClient = webDelegate

            //This will allow the tracing of links
            view.webViewObject.settings.javaScriptEnabled = true;
            view.webViewObject.settings.javaScriptCanOpenWindowsAutomatically = true;

            //plug in band and name
            view.webViewObject.loadUrl("https://www.youtube.com/watch?v=" + videoID)

        }
    }
}

class Delegate : WebViewClient()
{
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
    {
        super.onPageStarted(view, url, favicon)
        println("started")
    }
    override fun onPageFinished(view: WebView, url: String)
    {
        super.onPageFinished(view,url)
        println("finish")
    }
    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError)
    {
        println("Issue")

    }
    override fun onReceivedHttpError(
        view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse
    )
    {
        println(errorResponse.data)
    }
    override fun onReceivedSslError(
        view: WebView, handler: SslErrorHandler,
        error: SslError
    )
    {
        println(error.primaryError)
    }
}