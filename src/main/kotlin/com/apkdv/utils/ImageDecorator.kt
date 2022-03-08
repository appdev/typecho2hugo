package com.apkdv.utils

import com.youbenzi.mdtool.markdown.BlockType
import com.youbenzi.mdtool.markdown.bean.Block
import com.youbenzi.mdtool.markdown.bean.ValuePart

class ImageDecorator {
    private val allImage = arrayListOf<String>()

    private fun commonTextParagraph(valueParts: Array<ValuePart>, needPTag: Boolean) {
        return oneLineHtml(valueParts, if (needPTag) "p" else null)
    }

    private fun hasLink(types: Array<BlockType>?): Boolean {
        if (types == null) {
            return false
        }
        for (blockType in types) {
            if (blockType == BlockType.LINK) {
                return true
            }
        }
        return false
    }

    private fun oneLineHtml(valueParts: Array<ValuePart>, tagName: String?) {
        for (valuePart in valueParts) {
            val types = valuePart.types
            var value = valuePart.value
            if (hasLink(types)) {
                value = valuePart.title
            }
            if (types != null) {
                for (type in types) {
                    formatByType(type, value, valuePart)
                }
            }
        }
    }


    fun decorate(list: MutableList<Block>) {
        list.forEach {
            when (it.type) {
                BlockType.LIST, BlockType.TABLE, BlockType.QUOTE, BlockType.CODE, BlockType.HEADLINE -> {
                }
                else -> {
                    commonTextParagraph(it.valueParts, true)
                }
            }
        }
    }

    fun getOutImages(): ArrayList<String> {
        return allImage
    }

    private fun formatByType(type: BlockType, value: String?, valuePart: ValuePart) {
        if (type == BlockType.IMG) {
            allImage.add(valuePart.url)
        }
    }
}