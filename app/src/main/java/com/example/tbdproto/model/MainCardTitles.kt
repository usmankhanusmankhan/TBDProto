package com.example.tbdproto.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainCardTitles(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
