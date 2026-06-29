package com.jongchan.androidarchi.favorite.data.local

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import androidx.core.content.edit

/**
 * SharedPreferences 기반 Key-Value 저장소.
 *
 * 읽기는 [getString] / [observeString] 로 key 의 JSON 문자열을 그대로 노출하고,
 * 즐겨찾기 쓰기([insert]/[deleteByUrl])는 [KEY_SAVED_FAVORITE_ITEMS] 한 키의
 * JSON List 를 read-modify-write 한다. 동시 토글로 인한 lost update 를 막기 위해
 * 쓰기 전체를 [writeMutex] 로 직렬화한다.
 */
class FavoriteKVStorage(
    private val sharedPreferences: SharedPreferences,
    private val json: Json,
) {
    private val writeMutex = Mutex()

    fun getString(key: String): String? = sharedPreferences.getString(key, null)

    fun observeString(key: String): Flow<String?> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
            if (changedKey == key) {
                trySend(prefs.getString(key, null))
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        trySend(sharedPreferences.getString(key, null))
        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    suspend fun insert(item: FavoriteItemDto) = writeMutex.withLock {
        withContext(Dispatchers.IO) {
            val current = decodeFavorites()
            val updated = buildList(current.size + 1) {
                add(item)
                current.forEach { if (it.url != item.url) add(it) }
            }
            writeFavorites(updated)
        }
    }

    suspend fun deleteByUrl(url: String) = writeMutex.withLock {
        withContext(Dispatchers.IO) {
            val current = decodeFavorites()
            val updated = current.filterNot { it.url == url }
            if (updated.size != current.size) writeFavorites(updated)
        }
    }

    private fun decodeFavorites(): List<FavoriteItemDto> {
        val raw = sharedPreferences.getString(KEY_SAVED_FAVORITE_ITEMS, null) ?: return emptyList()
        return runCatching { json.decodeFromString<List<FavoriteItemDto>>(raw) }
            .getOrDefault(emptyList())
    }

    private fun writeFavorites(items: List<FavoriteItemDto>) {
        sharedPreferences.edit {
            putString(KEY_SAVED_FAVORITE_ITEMS, json.encodeToString(items))
        }
    }

    companion object {
        const val KEY_SAVED_FAVORITE_ITEMS = "saved_favorite_items"
    }
}
