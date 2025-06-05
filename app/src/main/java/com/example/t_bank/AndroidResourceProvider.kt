package com.example.t_bank

import android.content.Context

class AndroidResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}
