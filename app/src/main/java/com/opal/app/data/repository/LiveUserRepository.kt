package com.opal.app.data.repository

import com.opal.app.domain.model.DomainReferredFriend
import com.opal.app.domain.model.DomainUser
import com.opal.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LiveUserRepository : UserRepository {
    override fun getCurrentUser(): Flow<DomainUser> = flowOf(currentUser)

    val currentUser =
        DomainUser(
            id = "123",
            name = "Cave Johnson",
            email = "cave.johnson@aperturescience.com",
            picture = "https://ui-avatars.com/api/?name=CaveJohnson&background=random",
            referredFriendList =
                listOf(
                    DomainReferredFriend(
                        name = "Caroline",
                        picture = "https://ui-avatars.com/api/?name=Caroline&background=random",
                    ),
                    DomainReferredFriend(
                        name = "Chell",
                        picture = "https://ui-avatars.com/api/?name=Chell&background=random",
                    ),
                    DomainReferredFriend(
                        name = "Wheatley",
                        picture = "https://ui-avatars.com/api/?name=Wheatley&background=random",
                    ),
                    DomainReferredFriend(
                        name = "Doug Rattmann",
                        picture = "https://ui-avatars.com/api/?name=DougRattmann&background=random",
                    ),
                    DomainReferredFriend(
                        name = "Companion Cube",
                        picture = "https://ui-avatars.com/api/?name=CompanionCube&background=random",
                    ),
                ),
        )
}
