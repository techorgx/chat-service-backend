package com.techorgx.api.model

data class Payload (
    val sourceUsername: String,
    val destinationUsername: String,
    val message: String
) {
    constructor() : this("", "", "")
}