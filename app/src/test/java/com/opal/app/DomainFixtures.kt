package com.opal.app

import com.opal.app.domain.model.DomainReferredFriend
import com.opal.app.domain.model.DomainReward
import com.opal.app.domain.model.DomainUser

class DomainFixtures {

    object DomainRewardUtil {
        fun createList(): List<DomainReward> {
            return listOf(
                DomainReward(
                    id = "1",
                    referralNumber = 1,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Loyal Gem",
                    subtitle = "Unlock this special milestone",
                    claimed = true,
                ),
                DomainReward(
                    id = "3",
                    referralNumber = 3,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Soulful Gem",
                    subtitle = "Unlock this special milestone",
                    claimed = false,
                ),
                DomainReward(
                    id = "5",
                    referralNumber = 5,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "1 Year of Opal Pro",
                    subtitle = "Unlock one whole year of Opal Pro for Free",
                    claimed = false,
                ),
                DomainReward(
                    id = "10",
                    referralNumber = 10,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Popular Gem",
                    subtitle = "Unlock this special milestone",
                    claimed = false,
                ),
                DomainReward(
                    id = "20",
                    referralNumber = 20,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Special Gift",
                    subtitle = "Contact us to receive a special gift from the Opal Team",
                    claimed = false,
                ),
                DomainReward(
                    id = "100",
                    referralNumber = 100,
                    image = "https://cdn.gemperles.com/media/wysiwyg/Image_70.png",
                    title = "Mystery Gift",
                    subtitle = "Contact us to receive a life changing gift from Opal",
                    claimed = false,
                )
            )
        }
    }

    object DomainUserUtils {
        fun create(): DomainUser {
            return DomainUser(
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
    }
}
