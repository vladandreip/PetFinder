package com.example.petfinder.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedStorageImpl(context: Context) : Storage {

    private var masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun putDataString(key: String, data: String) {
        sharedPreferences.edit().putString(key, data).apply()
    }

    override fun getDataString(key: String): String =
        sharedPreferences.getString(key, EMPTY_SPACE) ?: EMPTY_SPACE

    companion object {
        private const val EMPTY_SPACE = ""
        private const val FILE_NAME = "secret_shared_prefs"
    }
}