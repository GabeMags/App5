package com.example.app5.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Item {

    @SerializedName("login")
    @Expose
    private var login: String? = null

    @SerializedName("avatar")
    @Expose
    private var avatarUrl: String? = null

    @SerializedName("html_url")
    @Expose
    private var htmlUrl: String? = null

    fun Item(login: String?, avatarUrl: String?, htmlUrl: String?) {
        this.login = login
        this.avatarUrl = avatarUrl
        this.htmlUrl = htmlUrl
    }

    fun getLogin(): String? {
        return login
    }

    fun setLogin(login: String?) {
        this.login = login
    }

    fun getAvatarUrl(): String? {
        return avatarUrl
    }

    fun setAvatarUrl(avatarUrl: String?) {
        this.avatarUrl = avatarUrl
    }

    fun getHtmlUrl(): String? {
        return htmlUrl
    }

    fun setHtmlUrl(htmlUrl: String?) {
        this.htmlUrl = htmlUrl
    }
}