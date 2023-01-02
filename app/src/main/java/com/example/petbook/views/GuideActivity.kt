package com.example.petbook.views

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.petbook.MainActivity
import com.example.petbook.R


class GuideActivity : AppCompatActivity() {
    lateinit var  myWebView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        val toolbar: Toolbar = findViewById(R.id.app_bar2)

        setSupportActionBar(toolbar)

        toolbar.setTitle("Blogs")
        toolbar.setNavigationOnClickListener(){


            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }
        myWebView = findViewById(R.id.webview)
        myWebView.webViewClient = WebViewClient()
        myWebView.settings.javaScriptEnabled = true
        myWebView.loadUrl("https://animalwellnessmagazine.com/category/blogs/")

        myWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                myWebView.loadUrl(
                    "javascript:(function() { " +
                            "var head = document.getElementsByClassName('td-footer-page')[0];"
                            + "head.parentNode.removeChild(head);" +
                            "})()"
                )
            }
        })
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

}