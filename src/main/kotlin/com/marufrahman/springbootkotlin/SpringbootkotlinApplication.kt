package com.marufrahman.springbootkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SpringbootkotlinApplication

fun main(args: Array<String>) {
	runApplication<SpringbootkotlinApplication>(*args)
}

@RestController
class MessageResource(val service: MessageService) {
	@GetMapping
	fun index(): List<MessageEntity> = service.findMessages()

	@PostMapping
	fun post(@RequestBody message: MessageEntity) {
		service.post(message)
	}
}

@Table("MESSAGES")
data class MessageEntity(@Id val id: String?, val text: String)

interface MessageRepository : CrudRepository<MessageEntity, String> {

	@Query("select * from MESSAGES")
	fun findMessages(): List<MessageEntity>
}

@Service
class MessageService(val db: MessageRepository) {
	fun findMessages(): List<MessageEntity> = db.findMessages()

	fun post(message: MessageEntity) {
		db.save(message)
	}
}