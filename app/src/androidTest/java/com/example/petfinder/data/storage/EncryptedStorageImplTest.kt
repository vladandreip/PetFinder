package com.example.petfinder.data.storage

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class EncryptedStorageImplTest {

    private lateinit var encryptedStorage: Storage

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        encryptedStorage = EncryptedStorageImpl(context)
    }

    @Test
    fun storage_does_not_manipulate_data_to_store() {
        val key = "test_key"
        val data = "test_data"

        encryptedStorage.putDataString(key, data)
        val result = encryptedStorage.getDataString(key)

        assertEquals(data, result)
    }
}