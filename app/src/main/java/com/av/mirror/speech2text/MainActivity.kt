package com.av.mirror.speech2text


import java.util.ArrayList
import java.util.Locale

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private var txtSpeechInput: TextView? = null
    private var btnSpeak: ImageButton? = null
    private val REQ_CODE_SPEECH_INPUT = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtSpeechInput = findViewById(R.id.txtSpeechInput) as TextView
        btnSpeak = findViewById(R.id.btnSpeak) as ImageButton

        // hide the action bar
        // getActionBar().hide();

        btnSpeak!!.setOnClickListener(
                object : View.OnClickListener {

                    override fun onClick(v: View) {
                        promptSpeechInput()
                    }
                })

    }

    /**
     * Showing google speech input dialog
     */
    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt))
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(applicationContext, getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Receiving speech input
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    val result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    txtSpeechInput!!.text = result[0]
                }
            }
        }
    }

    /* @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

}