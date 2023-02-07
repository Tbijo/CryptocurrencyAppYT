package com.plcoding.cryptocurrencyappyt.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinDto(
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)
// POUZIJE SA NA ZISKANIE DAT
// data class pre cele data co pridu ak nechceme vsetky
// v domain modely mozeme tieto data zredukovat