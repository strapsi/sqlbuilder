package de.maxpower.sqlbuilder.condition

abstract class Condition {

    fun and(initializer: Condition.() -> Unit) {
        addCondition(AndCondition().apply(initializer))
    }

    fun or(initializer: Condition.() -> Unit) {
        addCondition(OrCondition().apply(initializer))
    }

    open infix fun String.eq(value: Any?) {
        addCondition(EqCondition(this, value))
    }

    protected abstract fun addCondition(condition: Condition)
}
