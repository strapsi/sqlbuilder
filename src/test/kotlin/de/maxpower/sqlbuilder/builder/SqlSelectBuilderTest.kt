package de.maxpower.sqlbuilder.builder

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec


class SqlSelectBuilderTest : StringSpec({
    "sql select builder should build a sql string" {
        SqlSelectBuilder().build() shouldBe ""
    }
})
