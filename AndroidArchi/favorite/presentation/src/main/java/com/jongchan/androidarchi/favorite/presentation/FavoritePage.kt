package com.jongchan.androidarchi.favorite.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jongchan.androidarchi.common.presentation.R
import com.jongchan.androidarchi.common.presentation.component.ArchiText
import com.jongchan.androidarchi.common.presentation.searchList.ContentsGrid
import com.jongchan.androidarchi.common.presentation.ui.theme.DesignSystemThemeImpl

@Composable
fun FavoritePage(viewModel: FavoriteViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritePageContent(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
private fun FavoritePageContent(
    uiState: FavoriteUIState,
    onIntent: (FavoriteIntent) -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(DesignSystemThemeImpl.designSystemColor.bgDefaultLevel1)) {
        when {
            uiState.isLoading && uiState.favoriteItemList.isEmpty() -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            uiState.favoriteItemList.isEmpty() -> {
                ArchiText(
                    text = stringResource(R.string.favorite_empty),
                    style = DesignSystemThemeImpl.typeScale.textRegularL,
                    color = DesignSystemThemeImpl.designSystemColor.contentDefaultLevel3,
                    maxLines = Int.MAX_VALUE,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            else -> {
                ContentsGrid(
                    items = uiState.favoriteItemList,
                    onItemClick = { onIntent(FavoriteIntent.OpenFullScreen(it.contentsUrl)) },
                    onFavoriteClick = { item, _ ->
                        onIntent(FavoriteIntent.DeleteFavorite(item.contentsUrl))
                    },
                )
            }
        }
    }
}
