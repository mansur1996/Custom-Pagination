package com.example.pagination.model

data class UserData(
	val perPage: Int,
	val total: Int,
	val data: List<Data>,
	val page: Int,
	val totalPages: Int,
	val support: Support
)
