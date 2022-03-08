package com.apkdv.utils

import com.apkdv.model.GiteeResult
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.*


private val client by lazy { OkHttpClient() }

fun downloadPicture(url: String): GiteeResult.Content {
    val request = Request.Builder().get()
        .url(url)
        .build()
    val response = client.newCall(request).execute()
    val inputStream = response.body!!.byteStream()
    val fos: FileOutputStream
    val file = File("images", getFileName(url))
    try {
        fos = FileOutputStream(file)
        fos.write(inputStream.readBytes())          //这里写成read错了无数次
        fos.flush();
        fos.close()
        return uploadImageToGitee(url, file)
    } catch (e: Exception) {
        throw e
    }
}

fun getFileName(input: String): String {
    val spStr = input.split("/")
    return spStr[spStr.size - 1]
}

private val gson by lazy { GsonBuilder().serializeNulls().disableHtmlEscaping().create() }

fun uploadImageToGitee(url: String, file: File): GiteeResult.Content {
    val b64Image = Base64.getEncoder().encodeToString(file.readBytes())
    val token = ""
    val userName = ""
    val repo = ""
    val path = "images/blog/${System.currentTimeMillis().toString()+file.name}"
    val requestBody: RequestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("access_token", token)
        .addFormDataPart("owner", userName)
        .addFormDataPart("repo", repo)
        .addFormDataPart("path", path)
        .addFormDataPart("content", b64Image)
        .addFormDataPart("message", file.name)
        .build()


    val request = Request.Builder().post(requestBody)
        .url("https://gitee.com/api/v5/repos/$userName/$repo/contents/$path")
        .build()
    val response = client.newCall(request).execute()
    if (response.code == 201 && response.message == "Created") {
        response.body?.string()?.let {
            val result = gson.fromJson(it, GiteeResult::class.java)
            if (result.content.downloadUrl.isNotEmpty()) {
                result.content.originalAddress = url
                return result.content
            } else {
                println("文件上传失败：")
                println(url)
                throw RuntimeException(response.toString())
            }
        } ?: throw RuntimeException(response.toString())
    } else {
        println("文件上传失败：")
        println(url)
        throw RuntimeException(response.toString())
    }
}