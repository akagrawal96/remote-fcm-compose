package com.notifications.mobiteq.domain.model

data class AppConfig(
    val url: String = "",
    val headers: Map<String, String> = emptyMap()
)