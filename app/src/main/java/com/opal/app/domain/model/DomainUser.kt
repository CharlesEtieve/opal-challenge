package com.opal.app.domain.model

data class DomainUser(
    val id: String,
    val name: String,
    val email: String,
    val picture: String,
    val referredFriendList: List<DomainReferredFriend>,
)
