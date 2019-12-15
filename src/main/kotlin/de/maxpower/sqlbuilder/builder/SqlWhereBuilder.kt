package de.maxpower.sqlbuilder.builder

data class WhereAndGroup(val condition: Pair<String, String>)

class SqlWhereBuilder : SqlBuilder {

    private val whereExpressions = mutableListOf<Pair<String, String>>()

    override fun build(): String {
//        val conditions = whereExpressions.joinToString { }
        return "where "
    }

    fun and(condition: SqlWhereBuilder.() -> Unit): SqlWhereBuilder {
        condition()
        return this
    }

    infix fun String.eq(value: Any): SqlWhereBuilder {
        whereExpressions.add(this to value.toSqlString())
        return this@SqlWhereBuilder
    }

    private fun Any.toSqlString(): String {
        return when (this) {
            is String -> "'$this'"
            is Number -> this.toString()
            is Boolean -> this.toString()
            else -> throw NotImplementedError("toSqlString not implemented for type")
        }
    }

}
