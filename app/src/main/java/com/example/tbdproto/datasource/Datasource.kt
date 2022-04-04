package com.example.tbdproto.datasource

import com.example.tbdproto.R
import com.example.tbdproto.model.MainCardTitles

class Datasource {

    fun loadCardTitles(): List<MainCardTitles> {
        return listOf<MainCardTitles>(
            MainCardTitles(R.string.start_run),
            MainCardTitles(R.string.random_run),
            MainCardTitles(R.string.last_run),
        )
    }
}