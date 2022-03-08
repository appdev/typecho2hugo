package com.apkdv.utils

import com.apkdv.model.Article
import com.apkdv.model.GiteeResult
import com.youbenzi.mdtool.export.HTMLDecorator
import com.youbenzi.mdtool.markdown.Analyzer
import java.io.File
import javax.swing.text.html.HTML

object WriteFiles {

    fun write(article: Article) {
        println("write article: ${article.title}")
        println("file name: ${article.slug}")
        // markdown 解析
        val list = Analyzer.analyze(article.text)
        val decorator = ImageDecorator()
        decorator.decorate(list)
        val result = arrayListOf<GiteeResult.Content>()
        decorator.getOutImages().forEach {
            result.add(downloadPicture(it))
        }

        if (result.isNotEmpty()) {
            article.image = result[0].downloadUrl
        }
        result.forEach {
            article.text = article.text.replace(it.originalAddress, it.downloadUrl,true)
        }
        // 去掉 tp 的 markdown 标签
        article.text = article.text.replace("<!--markdown-->", "")

        if (article.description.isEmpty()){
            val htmlDec = HTMLDecorator()
            htmlDec.decorate(list)
            article.description = HtmlUtils.delHTMLTag(htmlDec.outputHtml()).take(70)
        }
        article.description = article.description.replace("\n", "").replace("\r", "")
            .replace("\t", "").replace(" ", "").replace("\r\n", "")
            .replace(" ", "").replace("#", "")
            .replace("`", "").replace("*", "").replace("\"", "'")
        val filename = "${article.slug}.md"
        val file = File("./file/$filename")
        file.writeText(buildString(article))
        println("write end article: ${article.slug}")
        println("----------------------------------")
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
description: "${item.description}"
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