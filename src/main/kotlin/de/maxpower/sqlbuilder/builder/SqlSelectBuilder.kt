package de.maxpower.sqlbuilder.builder

import de.maxpower.sqlbuilder.condition.AndCondition
import de.maxpower.sqlbuilder.condition.Condition
import de.maxpower.sqlbuilder.condition.JoinAndCondition
import de.maxpower.sqlbuilder.core.FieldAlias
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

class SqlSelectBuilder : SqlBuilder {
    private var tableName: String = ""
    private val fields = mutableSetOf<FieldAlias>()
    private var condition: Condition? = null
    private val joins = mutableListOf<SqlJoin>()

    override fun build(): String {
        if (tableName.isBlank()) throw IllegalArgumentException("tablename must not be empty")
        ensureFields()

        val fields = fields.joinToString(", ")
        val joinCondition = if (joins.isEmpty()) "" else joins.toSqlString()
        val whereCondition = if (condition == null) "" else "where\n\t$condition"

        return "select\n\t$fields\nfrom $tableName\n$joinCondition\n$whereCondition".trim()
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

    fun columns(vararg fieldNames: String): SqlSelectBuilder {
        return columns(fieldNames.toList())
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

    fun where(initializer: Condition.() -> Unit) {
        condition = AndCondition().apply(initializer)
    }

    fun join(type: JoinType, tableName: String, alias: String = "", initializer: Condition.() -> Unit) {
        joins.add(SqlJoin(type, tableName, alias, JoinAndCondition().apply(initializer)))
    }
}

class SqlJoin(val type: JoinType, val tableName: String, val alias: String, val condition: Condition?) {
    init {
        if (condition == null) throw RuntimeException("error with join condition")
    }
}

enum class JoinType(value: String) {
    Inner("inner"), Outer("outer")
}

fun MutableList<SqlJoin>.toSqlString(): String = this.joinToString(separator = "\n") {
    val joinType = when (it.type) {
        JoinType.Inner -> "inner join"
        JoinType.Outer -> "outer join"
    }
    "$joinType ${it.tableName} on\n\t${it.condition}"
}
