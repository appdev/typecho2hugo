package com.apkdv.model

import com.apkdv.utils.DatabaseFactory
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.selectAll

//CREATE TABLE typecho_relationships ( "cid" int(10) NOT NULL ,
//"mid" int(10) NOT NULL );
//
//CREATE UNIQUE INDEX typecho_relationships_cid_mid ON typecho_relationships ("cid", "mid");
object Relationships : IntIdTable(name = "typecho_relationships",columnName = "cid") {
    val mid = integer("mid")
}


data class RelatData(val cid: Int, val mid: Int)

val relatMap = hashMapOf<Int,ArrayList<Int>>()
suspend fun findAllRelat() {
    DatabaseFactory.dbOperate {
        Relationships.selectAll().forEach {
            val id = it[Relationships.id].value
            val mid = it[Relationships.mid]
            if (relatMap.containsKey(id)) {
                relatMap[id]?.add(mid)
            } else {
                relatMap[id] = arrayListOf(mid)
            }
        }
    }

}