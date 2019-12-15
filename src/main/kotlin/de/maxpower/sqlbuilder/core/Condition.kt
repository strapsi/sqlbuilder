package de.maxpower.sqlbuilder.core

abstract class Condition {
    abstract fun addCondition(condition: Condition)
    infix fun String.eq(value: Any?) {
        addCondition(Eq(this, value))
    }
}

class Eq(private val fieldName: String, private val value: Any?) : Condition() {
    override fun addCondition(condition: Condition) {}
}
