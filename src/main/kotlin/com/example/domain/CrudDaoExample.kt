package com.example.domain

import MemberEntity
import MemberTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    delete()
}

fun createSessionFactory() {
    // データベースへの接続
    Database.connect(
        "jdbc:mysql://127.0.0.1:3306/exposed_example",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "mysql"
    )
}

fun selectList() {
    createSessionFactory()
    transaction {
        val list = MemberEntity.all().map { MemberModel(it) }
        list.forEach {
            println(it)
        }
    }
}

fun selectById() {
    createSessionFactory()
    transaction {
        val entity = MemberEntity.findById(2)
        entity?.let { println(MemberModel(it)) }
    }
}

fun selectByName() {
    createSessionFactory()
    transaction {
        val entity = MemberEntity.find { MemberTable.name eq "Kotlin" }.map { MemberModel(it) }
        println(entity)
    }
}

fun insert() {
    createSessionFactory()
    transaction {
        val entity = MemberEntity.new {
            name = "Shiro"
        }
        println(MemberModel(entity))
    }
}

fun update() {
    createSessionFactory()
    transaction {
        // 更新対象のレコードを検索し、取得した結果の Entity クラスでプロパティの値を更新すると、データが更新される
        val entity = MemberEntity.findById(2)
        entity?.let { it.name = "Jiro"}
    }
}

fun delete() {
    createSessionFactory()
    transaction {
        val entity = MemberEntity.findById(3)
        entity?.delete()
    }
}
