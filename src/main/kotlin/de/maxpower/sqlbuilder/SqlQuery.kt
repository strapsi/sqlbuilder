package de.maxpower.sqlbuilder

import de.maxpower.sqlbuilder.builder.SqlBuilder
import de.maxpower.sqlbuilder.builder.SqlSelectBuilder

fun select(initializer: SqlSelectBuilder.() -> Unit): SqlBuilder {
    return SqlSelectBuilder().apply(initializer)
}
