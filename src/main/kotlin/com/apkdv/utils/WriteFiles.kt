package com.apkdv.utils

import com.apkdv.model.Article
import com.apkdv.model.GiteeResult
import java.io.File

object WriteFiles {

    fun write(article: Article) {
        println("write article: ${article.title}")
        println("file name: ${article.slug}")
        // markdown 解析
        val result = arrayListOf<GiteeResult.Content>()
        val list = MarkDownParse.parse(article.text)
        list.forEach {
            result.add(downloadPicture(it))
        }

        if (result.isNotEmpty()) {
            article.image = result[0].downloadUrl
        }
        result.forEach {
            article.text = article.text.replace(it.originalAddress, it.downloadUrl, true)
        }
        // 去掉 tp 的 markdown 标签
        article.text = article.text.replace("<!--markdown-->", "")


        val filename = "${article.slug}.md"
        val file = File("./file/$filename")
        file.writeText(buildString(article))
        println("write end article: ${article.slug}")
        println("----------------------------------")
    }


    fun writeContent(file: File) {
        var content = file.readText()
        println("write article: ${file.name}")
        // markdown 解析
        val result = arrayListOf<GiteeResult.Content>()
        val list = MarkDownParse.parse(content)
        list.forEach {
            if (!it.startsWith("https://gitee.com/huclengyue/my-gallery") &&
                !it.startsWith("https://static.apkdv.com/usr/uploads")
            ) {
                result.add(downloadPicture(it))
            }

            result.forEach {
                content = content.replace(it.originalAddress, it.downloadUrl, true)
            }

            val filename = file.name
            val file = File("./file/$filename")
            file.writeText(content)
            println("write end article: ${file.name}")
            println("----------------------------------")
        }
    }

    private fun buildString(item: Article): String {
        return """
---
title: "${item.title}"
slug: "${item.slug}"
date: ${item.created}T17:16:07+08:00
categories: [${item.categories.joinToString(",")}]
tags: [${item.categories.joinToString(",")}]
showToc: true
TocOpen: true
draft: false
${
            if (item.image?.isNotEmpty() == true) {
                """
cover: 
    image: "${item.image}"
    # alt: "alt text" # image alt text
    # caption: "display caption under cover" # display caption under cover
    relative: false # when using page bundles set this to true
            """.trimIndent()
            } else {
                ""
            }
        }
---
                
${item.text}
        """.trimIndent()
    }

}