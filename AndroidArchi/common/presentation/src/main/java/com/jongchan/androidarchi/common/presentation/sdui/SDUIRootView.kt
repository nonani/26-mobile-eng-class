package com.jongchan.androidarchi.common.presentation.sdui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jongchan.androidarchi.common.domain.sdui.SDUIViewType
import com.jongchan.androidarchi.common.domain.sdui.SDUIViewTypeVO
import com.jongchan.androidarchi.common.domain.sdui.StartImageTitleDescActionIconViewTypeVO
import com.jongchan.androidarchi.common.domain.sdui.TitleDescEndImageViewTypeVO
import com.jongchan.androidarchi.common.domain.sdui.TitleDescViewTypeVO

@Composable
fun SDUIRootView(modifier: Modifier, items: List<SDUIViewTypeVO>) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = items, key = { it.hashCode() }) { item ->
            when (item.viewType) {
                SDUIViewType.TitleDescriptionViewType -> TitleDescView(item.content as TitleDescViewTypeVO)
                SDUIViewType.TitleDescriptionEndImageViewType -> TitleDescEndImageView(item.content as TitleDescEndImageViewTypeVO)
                SDUIViewType.StartImageTitleDescriptionActionIconType -> StartImageTitleDescActionIconView(
                    item.content as StartImageTitleDescActionIconViewTypeVO
                )
            }
        }
    }
}