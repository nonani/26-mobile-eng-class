package com.jongchan.androidarchi.common.domain.favorite

import com.jongchan.androidarchi.common.domain.base.BaseUseCase
import com.jongchan.androidarchi.common.domain.helper.MessageHelper
import com.jongchan.androidarchi.common.domain.helper.NavigationHelper
import com.jongchan.androidarchi.common.domain.helper.ResourceHelper
import com.jongchan.androidarchi.common.domain.helper.StringResource
import com.jongchan.androidarchi.common.domain.message.IconType
import com.jongchan.androidarchi.common.entity.favorite.FavoriteItemVO
import com.jongchan.androidarchi.tti.TTIHelper
import javax.inject.Inject

class RegisterFavoriteItemUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    resourceHelper: ResourceHelper,
    messageHelper: MessageHelper,
    navigationHelper: NavigationHelper,
    ttiHelper: TTIHelper,
) : BaseUseCase(resourceHelper, messageHelper, navigationHelper, ttiHelper) {
    suspend operator fun invoke(favoriteItem: FavoriteItemVO): Result<Unit> =
        runCatching { favoriteRepository.createFavoriteItem(favoriteItem) }
            .onFailure {
                messageHelper.showSnackBar(
                    iconType = IconType.ERROR,
                    messageText = resourceHelper.getString(StringResource.FAVORITE_REGISTER_FAILED),
                )
            }
}
