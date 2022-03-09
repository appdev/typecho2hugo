package com.apkdv.model

import com.apkdv.utils.DatabaseFactory
import com.apkdv.utils.TimeUtils
import com.apkdv.utils.WriteFiles.write
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

//CREATE TABLE typecho_contents ( "cid" INTEGER NOT NULL PRIMARY KEY,
//"title" varchar(200) default NULL ,
//"slug" varchar(200) default NULL ,
//"created" int(10) default '0' ,
//"modified" int(10) default '0' ,
//"text" text ,
//"order" int(10) default '0' ,
//"authorId" int(10) default '0' ,
//"template" varchar(32) default NULL ,
//"type" varchar(16) default 'post' ,
//"status" varchar(16) default 'publish' ,
//"password" varchar(32) default NULL ,
//"commentsNum" int(10) default '0' ,
//"allowComment" char(1) default '0' ,
//"allowPing" char(1) default '0' ,
//"allowFeed" char(1) default '0' ,
//"parent" int(10) default '0' , `viewsNum` INT(10) DEFAULT 0,
//`views` INT(10) DEFAULT 0, `likes` INT(10) DEFAULT 0);
/**
 * 文章
 */
object Contents : IntIdTable(name = "typecho_contents", columnName = "cid") {
    val title = varchar("title", 255)
    val slug = varchar("slug", 200)
    val created = integer("created")
    val text = text("text")
    val type = varchar("type", 16)
    val status = varchar("status", 16)
    val password = varchar("password", 32).nullable()
}

class Content(cid: EntityID<Int>) : IntEntity(cid) {
    companion object : IntEntityClass<Content>(Contents)

    var title by Contents.title
    var slug by Contents.slug
    var created by Contents.created
    var text by Contents.text
    var type by Contents.type
    var status by Contents.status
    var password by Contents.password
}

data class Article(
    var title: String,
    var slug: String,
    var created: String,
    var text: String,
    var tags: MutableList<String>,
    var categories: MutableList<String>,
    var description: String,
    var image: String? = "",
)


suspend fun findAll() {
    DatabaseFactory.dbOperate {
        val list = Content.find { Contents.type eq "post" }.toMutableList()
        val allList = arrayListOf<Article>()
        list.forEach { it ->
            if (it.status == "publish") {
                val result = findTagAndCategory(it.id.value)
                val article = Article(
                    title = it.title,
                    slug = it.slug,
                    created = TimeUtils.millis2String(it.created.toLong()),
                    text = it.text,
                    tags = result.tags.map { it.name }.toMutableList(),
                    categories = result.category.map { it.name }.toMutableList(),
                    description = "",
                    image = ""
                )
                allList.add(article)
                // write
//                if (article.slug == "gold-coin-drop-animation")
                    write(article)
            }

        }

    }
}
