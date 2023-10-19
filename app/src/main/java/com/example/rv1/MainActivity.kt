package com.example.rv1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        const val SPAN_COUNT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

//        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = SpeedyLinearLayoutManager(this)

        recyclerView.adapter = CustomRecyclerAdapter(getCatList(), SPAN_COUNT)

        recyclerView.requestFocus()
    }

    private fun getCatList(): List<String> {
        val res = mutableListOf<String>()
        val cats = this.resources.getStringArray(R.array.cat_names).toList()
        for (i in 1..10) {
            for (cat in cats) {
                res.add("$cat $i")
            }
        }
        return res
    }
}