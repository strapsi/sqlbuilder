package de.maxpower.sqlbuilder.condition

class JoinOrCondition: OrCondition() {
    override infix fun String.eq(value: Any?) {
        addCondition(JoinEqualsCondition(this, value))
    }
}
