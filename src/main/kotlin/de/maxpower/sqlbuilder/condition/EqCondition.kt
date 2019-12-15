package de.maxpower.sqlbuilder.condition

open class EqCondition(private val column: String, private val value: Any?) : Condition() {

    override fun addCondition(condition: Condition) {
        throw IllegalStateException("Can't add a nested condition to the sql 'eq'")
    }

    override fun toString(): String {
        return "$column = " + when (value) {
            null -> "null"
            is String -> "'$value'"
            is Number -> value.toString()
            is Boolean -> value.toString()
            else -> throw NotImplementedError("toSqlString not implemented for type ${value.javaClass.name}")
        }
    }
}
