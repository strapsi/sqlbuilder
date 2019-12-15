package de.maxpower.sqlbuilder.builder

import de.maxpower.sqlbuilder.select
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import io.kotlintest.specs.BehaviorSpec
import io.kotlintest.specs.FunSpec
import io.kotlintest.specs.StringSpec
import nonapi.io.github.classgraph.utils.Join
import java.lang.IllegalArgumentException

class T : FunSpec({
    context("simple select from") {
        test("it should do good stuff") {
            "a" shouldBe "b"
        }
        test("bls") {
            "a" shouldBe "b"
        }
    }
})

class SqlSelectBuilderTest : StringSpec({
    "it should return a sql select all statement with asterix syntax" {
        select {
            `*`()
            from("table")
        }.build() shouldBe "select * from table"
    }

    "it should return a sql select all statement with all syntax" {
        select {
            all()
            from("table")
        }.build() shouldBe "select * from table"
    }

    "it should return a sql select all statement if no fields are specified" {
        select {
            from("table")
        }.build() shouldBe "select * from table"
    }

    "it should select specific fields with an alias" {
        select {
            "hund" alias "dog"
            "frau" alias "frau"
            from("table")
        }.build() shouldBe "select hund as dog, frau as frau from table"
    }

    "it should select specific fields and all fields from a table" {
        select {
            column("hund")
            `*`()
            from("table")
        }.build() shouldBe "select hund, * from table"
    }

    "it should select specific fields and all fields with asterix syntax" {
        select {
            column("hund")
            `*`()
            from("table")
        }.build() shouldBe "select hund, * from table"
    }

    "it should add a list of columns" {
        select {
            columns(listOf("heidi"))
            from("table")
        }.build() shouldBe "select heidi from table"
    }

    "it should throw an exception if an empty columns list is provided" {
        val exception = shouldThrow<IllegalArgumentException> {
            select {
                columns(emptyList())
                from("table")
            }
        }
        exception.message shouldBe "empty columns list. at least one column must be specified"
    }

    "it should throw an exception if an empty tablename is given" {
        val exception = shouldThrow<IllegalArgumentException> {
            select {}.build()
        }
        exception.message shouldBe "tablename must not be empty"
    }

    "it should throw an exception if no fieldname is given  with alisa syntax" {
        val exception = shouldThrow<IllegalArgumentException> {
            select {
                "" alias "heidi"
                from("table")
            }.build()
        }
        exception.message shouldBe "fieldname must not be empty"
    }

    "it should throw an exception if no alias is given with alias syntax" {
        val exception = shouldThrow<IllegalArgumentException> {
            select {
                "klaus" alias ""
                from("table")
            }
        }
        exception.message shouldBe "alias must not be empty"
    }

    "it should have a where condition" {
        select {
            `*`()
            from("table")
            where {
                "ulf" eq 13
            }
        }.build() shouldBe "select * from table where ulf = 13"
    }

    "join" {
        select {
            `*`()
            from("table")
            join(JoinType.Inner, "second") {
                "a" eq "b"
            }
            join(JoinType.Outer, "outer_table") {
                "table.q" eq "outer_table.k"
                "table.q" eq "outer_table.xx"
            }
            where {
                "c" eq 1337
                "kuh" eq "muh"
            }
        }.build() shouldBe "select * from table where ulf = 13"
    }
})
