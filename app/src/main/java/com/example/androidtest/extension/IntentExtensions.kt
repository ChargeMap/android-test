package com.example.androidtest.extension

import android.content.Intent

fun Intent.getStringExtra(name: String, defaultValue: String): String {
    return this.getStringExtra(name) ?: defaultValue
}
