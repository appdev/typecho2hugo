package com.apkdv.utils

import org.commonmark.node.Image
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.AttributeProvider
import org.commonmark.renderer.html.HtmlRenderer


object MarkDownParse {
    fun parse(markdown: String): ArrayList<String> {
        val images = ArrayList<String>()
        val parser = Parser.builder().build();
        val document = parser.parse(markdown);
        val renderer = HtmlRenderer.builder()
            .attributeProviderFactory {
                HtmlAttributeProvider {
                    images.add(it)
                }
            }
            .build()
        renderer.render(document)
        return images
    }
}

internal class HtmlAttributeProvider(val onImageNode: (String) -> Unit) : AttributeProvider {
    override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
        if (node is Image) {
            onImageNode.invoke(node.destination)
        }
    }
}