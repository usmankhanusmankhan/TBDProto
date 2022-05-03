package com.example.tbdproto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.tbdproto.adapter.ItemAdapter
import com.example.tbdproto.datasource.Datasource

class MainActivity : AppCompatActivity() {
    // NOTE: I changed the Launcher activity to MapActivity in the AndroidManifest. Revert back to MainActivity.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDataset = Datasource().loadCardTitles()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)
    }
}