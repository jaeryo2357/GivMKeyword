package com.mut_jaeryo.domain.entities

data class Drawing(
        val id: String = "",
        val keyword: String,
        val userName: String,
        val imageUrl: String = "",
        val content: String,
        val heartCount: Int = 0,
        val isHeart: Boolean = false
        )
