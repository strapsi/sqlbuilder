package de.maxpower.sqlbuilder

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import java.lang.IllegalArgumentException


class SqlQueryKtTest : StringSpec({
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
})
