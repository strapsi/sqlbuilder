package de.maxpower.sqlbuilder.condition

class JoinEqualsCondition(private val column: String, private val value: Any?) : EqCondition(column, value) {
    override fun toString(): String {
        return "$column = " + when (value) {
            null -> "null"
            is String -> value
            else -> throw NotImplementedError("toSqlString not implemented for type ${value.javaClass.name}")
        }
    }
}
