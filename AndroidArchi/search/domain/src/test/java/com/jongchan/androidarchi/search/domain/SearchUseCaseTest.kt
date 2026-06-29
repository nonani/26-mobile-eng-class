package com.jongchan.androidarchi.search.domain

import com.jongchan.androidarchi.common.domain.media.MediaSearchRepository
import com.jongchan.androidarchi.common.entity.media.MediaItemVO
import com.jongchan.androidarchi.common.entity.media.MediaSearchResultVO
import com.jongchan.androidarchi.common.entity.media.MediaType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchUseCaseTest {

    private lateinit var mediaSearchRepository: MediaSearchRepository
    private lateinit var useCase: SearchUseCase

    @Before
    fun setUp() {
        mediaSearchRepository = mockk(relaxed = true)
        useCase = SearchUseCase(
            mediaSearchRepository,
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true),
        )
    }

    private fun mediaItemList() = listOf(
        MediaItemVO(type = MediaType.IMAGE, title = "t1", urlKey = "u1"),
        MediaItemVO(type = MediaType.VIDEO, title = "t2", urlKey = "u2"),
    )

    @Test
    fun `invoke - delegates to repository and returns its result`() = runTest {
        val expected = MediaSearchResultVO(items = mediaItemList(), isEnd = false)
        coEvery { mediaSearchRepository.search("cat", 1) } returns expected

        val result = useCase("cat", 1)

        assertEquals(expected, result)
        coVerify(exactly = 1) { mediaSearchRepository.search("cat", 1) }
    }

    @Test
    fun `invoke - propagates exception to caller`() = runTest {
        val expected = IllegalStateException("boom")
        coEvery { mediaSearchRepository.search(any(), any()) } throws expected

        val caught = runCatching { useCase("cat", 1) }.exceptionOrNull()

        assertTrue(caught is IllegalStateException)
        assertEquals(expected.message, caught?.message)
    }
}
