package com.techorgx.api.model

data class Payload (
    val username: String,
    val message: String
) {
    constructor() : this("", "")
}