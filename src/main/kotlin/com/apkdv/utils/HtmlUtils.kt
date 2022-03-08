package com.apkdv.utils

import java.util.regex.Pattern

object HtmlUtils {
    fun delHTMLTag(htmlStr: String): String {
        var htmlStr = htmlStr
        val regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>" //定义script的正则表达式
        val regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>" //定义style的正则表达式
        val regEx_html = "<[^>]+>" //定义HTML标签的正则表达式
        val p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE)
        val m_script = p_script.matcher(htmlStr)
        htmlStr = m_script.replaceAll("") //过滤script标签
        val p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE)
        val m_style = p_style.matcher(htmlStr)
        htmlStr = m_style.replaceAll("") //过滤style标签
        val p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE)
        val m_html = p_html.matcher(htmlStr)
        htmlStr = m_html.replaceAll("") //过滤html标签
        return htmlStr.trim { it <= ' ' } //返回文本字符串
    }

}