package de.maxpower.sqlbuilder.builder

import de.maxpower.sqlbuilder.core.FieldAlias
import java.lang.IllegalArgumentException

class SqlSelectBuilder : SqlBuilder {
    private var tableName: String = ""
    private val fields = mutableSetOf<FieldAlias>()
    private var whereStmt = ""

    override fun build(): String {
        if (tableName.isBlank()) throw IllegalArgumentException("tablename must not be empty")
        ensureFields()
        var sql = "select ${fields.joinToString(", ")} from $tableName"
        sql += whereStmt
        return sql
    }

    private fun ensureFields() {
        if (fields.isEmpty()) fields.add(FieldAlias("*"))
    }

    fun `*`(): SqlSelectBuilder = all()

    fun all(): SqlSelectBuilder {
        fields.add(FieldAlias("*"))
        return this
    }

    fun column(fieldName: String, alias: String = ""): SqlSelectBuilder {
        fields.add(FieldAlias(fieldName, alias))
        return this
    }

    fun columns(fieldNames: Collection<String>): SqlSelectBuilder {
        if (fieldNames.isEmpty()) throw IllegalArgumentException("empty columns list. at least one column must be specified")
        fields.addAll(fieldNames.map { FieldAlias(it) })
        return this
    }

    infix fun String.alias(alias: String): SqlSelectBuilder {
        if (this.isBlank()) throw IllegalArgumentException("fieldname must not be empty")
        if (alias.isBlank()) throw IllegalArgumentException("alias must not be empty")
        fields.add(FieldAlias(this, alias))
        return this@SqlSelectBuilder
    }

    fun from(tableName: String): SqlSelectBuilder {
        this.tableName = tableName
        return this
    }

    fun where(initializer: SqlWhereBuilder.() -> Unit): SqlBuilder {
        return SqlWhereBuilder().apply {
            initializer()
            whereStmt = this.build()
        }
    }
}
