package de.maxpower.sqlbuilder.builder

import de.maxpower.sqlbuilder.select
import io.kotlintest.specs.StringSpec


internal class SqlWhereBuilderTest : StringSpec({
    "test" {
        select {
            where {
                and {
                    "heide" eq 7
                    "klaus" eq "cool"
                }
            }
        }.build()
    }
})
