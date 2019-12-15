package de.maxpower.sqlbuilder

import de.maxpower.sqlbuilder.builder.SqlBuilder
import de.maxpower.sqlbuilder.builder.SqlSelectBuilder
import de.maxpower.sqlbuilder.builder.SqlWhereBuilder

fun select(initializer: SqlSelectBuilder.() -> Unit): SqlBuilder {
    return SqlSelectBuilder().apply(initializer)
}

fun where(initializer: SqlWhereBuilder.() -> Unit): SqlBuilder {
    return SqlWhereBuilder().apply(initializer)
}
