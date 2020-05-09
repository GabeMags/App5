package com.example.app5.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ItemResponse {

    @SerializedName("items")
    @Expose
    private var items: List<Item?>? = null

    fun getItems(): List<Item?>? {
        return items
    }

    fun setItems(items: List<Item?>?) {
        this.items = items
    }
}