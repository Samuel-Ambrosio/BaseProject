package com.ghrisav.data.local.session

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ghrisav.data.local.converters.JsonDateSerializer
import com.ghrisav.data.model.session.TokenBO
import com.google.gson.GsonBuilder
import java.util.*

class PreferenceUtils(private val context: Context) {

    companion object {
        private const val FILENAME_SHARED_PREFERENCES = "secret_shared_prefs"
        private const val KEY_TOKEN = "key_token"
        private const val KEY_DATETIME_TOKEN_SET = "key_datetime_token_set"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
    }

    private val keyGenParameterSpec = MasterKey.KeyScheme.AES256_GCM
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(keyGenParameterSpec).build()

    private val sharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            FILENAME_SHARED_PREFERENCES,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    private val editor by lazy { sharedPreferences.edit() }

    /* Public methods */
    fun saveUserAndToken(username: String, password: String, token: TokenBO) {
        with(editor) {
            putString(KEY_TOKEN, gsonBuilder().create().toJson(token))
            putLong(KEY_DATETIME_TOKEN_SET, System.currentTimeMillis())
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    fun saveToken(token: TokenBO) {
        with(editor) {
            putString(KEY_TOKEN, gsonBuilder().create().toJson(token))
            putLong(KEY_DATETIME_TOKEN_SET, System.currentTimeMillis())
            apply()
        }
    }

    fun getToken(): TokenBO? {
        val tokenJson = sharedPreferences.getString(KEY_TOKEN, null)
        tokenJson?.let {
            val token = gsonBuilder().create().fromJson(it, TokenBO::class.java)

            val dateTimeTokenSet =
                Date(sharedPreferences.getLong(KEY_DATETIME_TOKEN_SET, System.currentTimeMillis()))

            val tokenCalendar = Calendar.getInstance().apply {
                time = dateTimeTokenSet
                add(Calendar.SECOND, token.expiresIn.toInt())
            }
            val currentCalendar = Calendar.getInstance()

            return if (tokenCalendar > currentCalendar) {
                token
            } else {
                clearData()
                null
            }
        }

        return null
    }

    fun getUsername() = sharedPreferences.getString(KEY_USERNAME, null)

    fun getPassword() = sharedPreferences.getString(KEY_PASSWORD, null)

    fun clearData(): Boolean {
        with(editor) {
            remove(KEY_TOKEN)
            remove(KEY_DATETIME_TOKEN_SET)
            remove(KEY_USERNAME)
            remove(KEY_PASSWORD)
        }
        return editor.commit()
    }

    /* Private methods */
    private fun gsonBuilder(): GsonBuilder {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Date::class.java, JsonDateSerializer.DESERIALIZER)
        builder.registerTypeAdapter(Date::class.java, JsonDateSerializer.SERIALIZER)
        return builder
    }
}