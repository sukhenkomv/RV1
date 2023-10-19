package com.example.rv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CustomRecyclerAdapter(getCatList())

        recyclerView.requestFocus()
    }

    private fun fillist(): List<String> {
        val data = mutableListOf<String>()
        (0..30).forEach{
            data.add("$it element")
        }
        return data
    }

    private fun getCatList(): List<String> {
        return this.resources.getStringArray(R.array.cat_names).toList()
    }
}