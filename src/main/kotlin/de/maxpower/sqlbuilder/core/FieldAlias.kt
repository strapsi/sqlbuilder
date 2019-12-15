package de.maxpower.sqlbuilder.core

data class FieldAlias(val fieldName: String, val alias: String = "") {
    override fun toString(): String = if (alias.isBlank()) fieldName else "$fieldName as $alias"
}
