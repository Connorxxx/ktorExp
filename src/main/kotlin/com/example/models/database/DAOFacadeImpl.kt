package com.example.models.database

import com.example.models.Article
import com.example.models.Articles
import com.example.models.database.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToArticle(row: ResultRow) = Article(
        id = row[Articles.id],
        title = row[Articles.title],
        body = row[Articles.body]
    )
    override suspend fun allArticles() = dbQuery {
        Articles.selectAll().map(::resultRowToArticle)
    }



    override suspend fun article(id: Int) = dbQuery {
        Articles.select { Articles.id eq id }
            .map(::resultRowToArticle)
            .singleOrNull()
    }

    override suspend fun addNewArticle(title: String, body: String) = dbQuery {
        Articles.insert {
            it[Articles.title] = title
            it[Articles.body] = body
        }.resultedValues?.singleOrNull()?.let(::resultRowToArticle)
    }

    override suspend fun editArticle(id: Int, title: String, body: String) = dbQuery {
        Articles.update( { Articles.id eq id }) {
            it[Articles.title] = title
            it[Articles.body] = body
        } > 0
    }

    override suspend fun deleteArticle(id: Int) = dbQuery {
        Articles.deleteWhere { Articles.id eq id } > 0
    }
}