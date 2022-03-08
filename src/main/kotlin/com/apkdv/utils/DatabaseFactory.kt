package com.apkdv.utils

import com.apkdv.model.Contents
import com.apkdv.model.Metas
import com.apkdv.model.Relationships
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

object DatabaseFactory {
    val database: Database by lazy {
        Database.connect("jdbc:sqlite:data/data.db", "org.sqlite.JDBC")
    }

    fun init() {
        transaction(database) {
            addLogger(KotlinLoggingSqlLogger)
            create(Contents, Metas, Relationships)
        }
    }

    suspend fun <T> dbOperate(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction { block() }
    }
}

object KotlinLoggingSqlLogger : SqlLogger {
    private val logger = LoggerFactory.getLogger(KotlinLoggingSqlLogger::class.java)

    override
    fun log(context: StatementContext, transaction: Transaction) {
        logger.info("SQL: ${context.expandArgs(transaction)}")
    }
}