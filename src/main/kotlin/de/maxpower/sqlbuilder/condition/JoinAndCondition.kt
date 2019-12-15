package de.maxpower.sqlbuilder.condition

class JoinAndCondition : AndCondition() {
    override infix fun String.eq(value: Any?) {
        addCondition(JoinEqualsCondition(this, value))
    }
}
