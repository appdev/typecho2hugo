package com.apkdv.model

import com.apkdv.utils.DatabaseFactory
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.selectAll

//CREATE TABLE typecho_metas ( "mid" INTEGER NOT NULL PRIMARY KEY,
//"name" varchar(200) default NULL ,
//"slug" varchar(200) default NULL ,
//"type" varchar(32) NOT NULL ,
//"description" varchar(200) default NULL ,
//"count" int(10) default '0' ,
//"order" int(10) default '0' ,
//"parent" int(10) default '0');
//
//CREATE INDEX typecho_metas_slug ON typecho_metas ("slug");

object Metas : IntIdTable(name = "typecho_metas",columnName = "mid") {
    val name = varchar("name", 200)
    val type = varchar("type", 32)
}

data class MeatsData(val mid: Int, val name: String, val type: String)

val allMetas = arrayListOf<MeatsData>()
suspend fun findAllTag() {
    DatabaseFactory.dbOperate {
        allMetas.addAll(Metas.selectAll().map { MeatsData(it[Metas.id].value, it[Metas.name], it[Metas.type]) }
            .toList())
    }
}


fun findTagAndCategory(cid: Int): Result {
    val tags = arrayListOf<MeatsData>()
    val categorys = arrayListOf<MeatsData>()
    relatMap[cid]?.onEach {
        allMetas.forEach { m ->
            if (m.mid == it) {
                if (m.type == "category") {
                    categorys.add(m)
                } else {
                    tags.add(m)
                }
            }
        }
    }
    return Result(tags, categorys)
}

data class Result(val tags: ArrayList<MeatsData>, val category: ArrayList<MeatsData>)

