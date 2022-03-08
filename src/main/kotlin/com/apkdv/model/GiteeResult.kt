package com.apkdv.model
import com.google.gson.annotations.SerializedName

data class GiteeResult(
    @SerializedName("content")
    var content: Content
) {
    data class Content(
        @SerializedName("download_url")
        var downloadUrl: String = "", // https://gitee.com/huclengyue/my-gallery/raw/master/images/blog/summary.zh-cn.png
        @SerializedName("name")
        var name: String, // summary.zh-cn.png
        @SerializedName("path")
        var path: String, // images/blog/summary.zh-cn.png

        var originalAddress: String // images/blog/summary.zh-cn.png
    )
}