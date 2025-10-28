package com.opal.app.domain.model

data class DomainReward(
    val id: String,
    val referralNumber: Long,
    val image: String,
    val title: String,
    val subtitle: String,
    val claimed: Boolean,
)
