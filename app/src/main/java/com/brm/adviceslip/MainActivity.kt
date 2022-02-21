package com.brm.adviceslip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(HttpApi::class.java)

        val adviceTv = findViewById<TextView>(R.id.adviceTv)
        val adviceProgress = findViewById<ProgressBar>(R.id.progressBar)

        findViewById<Button>(R.id.adviceBtn).setOnClickListener {
            adviceProgress.isVisible = true
            api.getAdvice().enqueue(object : Callback<Advice>{
                override fun onResponse(call: Call<Advice>, response: Response<Advice>) {
                    adviceProgress.isVisible = false
                    if (response.code() == 200)
                        if (response.body() != null)
                            if (response.body()!!.slip.advice.isNotEmpty()) {
                                adviceTv.text = response.body()!!.slip.advice
                                return
                            }
                    adviceTv.text = "Oops, something went wrong :("
                }

                override fun onFailure(call: Call<Advice>, t: Throwable) {
                    adviceProgress.isVisible = false
                    adviceTv.text = "Oops, advice find error :("
                }
            })
        }

    }

    companion object {
        private const val BASE_URL = "https://api.adviceslip.com/"
    }
}