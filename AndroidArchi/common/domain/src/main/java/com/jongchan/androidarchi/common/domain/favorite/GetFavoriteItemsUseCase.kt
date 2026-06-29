package com.jongchan.androidarchi.common.domain.favorite

import com.jongchan.androidarchi.common.domain.base.BaseUseCase
import com.jongchan.androidarchi.common.domain.helper.MessageHelper
import com.jongchan.androidarchi.common.domain.helper.NavigationHelper
import com.jongchan.androidarchi.common.domain.helper.ResourceHelper
import com.jongchan.androidarchi.common.entity.favorite.FavoriteItemVO
import com.jongchan.androidarchi.tti.TTIHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteItemsUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    resourceHelper: ResourceHelper,
    messageHelper: MessageHelper,
    navigationHelper: NavigationHelper,
    ttiHelper: TTIHelper,
) : BaseUseCase(resourceHelper, messageHelper, navigationHelper, ttiHelper) {
    operator fun invoke(): Flow<List<FavoriteItemVO>> =
        favoriteRepository.getFavoriteItemsFlow()
}
