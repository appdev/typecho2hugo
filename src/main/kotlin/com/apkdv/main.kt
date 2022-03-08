package com.apkdv

import com.apkdv.model.findAll
import com.apkdv.model.findAllRelat
import com.apkdv.model.findAllTag
import com.apkdv.utils.DatabaseFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

suspend fun main() {
    val imageFile = File("images/")
    val articleFile = File("file/")
    println(imageFile.absoluteFile)
    if (!imageFile.exists()) {
        withContext(Dispatchers.IO) {
            Files.createDirectory(Paths.get(imageFile.absolutePath))
        }
    }
    if (!articleFile.exists()) {
        withContext(Dispatchers.IO) {
            Files.createDirectory(Paths.get(articleFile.absolutePath))
        }
    }
    DatabaseFactory.init()

    findAllTag()
    findAllRelat()

    findAll()
}