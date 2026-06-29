package com.jongchan.androidarchi.search.domain

import com.jongchan.androidarchi.common.domain.base.BaseUseCase
import com.jongchan.androidarchi.common.domain.helper.MessageHelper
import com.jongchan.androidarchi.common.domain.helper.NavigationHelper
import com.jongchan.androidarchi.common.domain.helper.ResourceHelper
import com.jongchan.androidarchi.common.domain.media.MediaSearchRepository
import com.jongchan.androidarchi.common.entity.media.MediaSearchResultVO
import com.jongchan.androidarchi.tti.TTIHelper
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val mediaSearchRepository: MediaSearchRepository,
    resourceHelper: ResourceHelper,
    messageHelper: MessageHelper,
    navigationHelper: NavigationHelper,
    ttiHelper: TTIHelper,
) : BaseUseCase(resourceHelper, messageHelper, navigationHelper, ttiHelper) {

    /**
     * 키워드로 이미지+동영상을 검색한다. 페이지 내부에서만 최신순 정렬된 한 페이지를 돌려준다.
     * 네트워크 오류는 호출부(ViewModel)에서 처리하도록 그대로 전파한다.
     */
    suspend operator fun invoke(query: String, page: Int): MediaSearchResultVO =
        mediaSearchRepository.search(query, page)
}
