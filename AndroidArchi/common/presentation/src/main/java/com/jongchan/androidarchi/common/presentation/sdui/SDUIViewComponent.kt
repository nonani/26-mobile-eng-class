package com.jongchan.androidarchi.common.presentation.sdui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import com.jongchan.androidarchi.common.domain.sdui.StartImageTitleDescActionIconViewTypeVO
import com.jongchan.androidarchi.common.domain.sdui.TitleDescEndImageViewTypeVO
import com.jongchan.androidarchi.common.domain.sdui.TitleDescViewTypeVO
import com.jongchan.androidarchi.common.presentation.component.ArchiText
import com.jongchan.androidarchi.common.presentation.imageUtil.rememberImageLoader
import com.jongchan.androidarchi.common.presentation.ui.theme.DesignSystemThemeImpl

@Composable
fun TitleDescView(viewTypeVO: TitleDescViewTypeVO) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.Red)
    ) {
        ArchiText(
            text = viewTypeVO.titleText,
            style = DesignSystemThemeImpl.typeScale.textStrongM,
            color = DesignSystemThemeImpl.designSystemColor.contentDefaultLevel3,
        )
        Spacer(modifier = Modifier.size(8.dp))
        ArchiText(
            text = viewTypeVO.descriptionText,
            style = DesignSystemThemeImpl.typeScale.textRegularM,
            color = DesignSystemThemeImpl.designSystemColor.contentDefaultLevel1,
        )
    }
}

@Composable
fun TitleDescEndImageView(viewTypeVO: TitleDescEndImageViewTypeVO) {
    val imageLoader = rememberImageLoader()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.Blue)
    ) {
        Column {
            ArchiText(
                text = viewTypeVO.titleText,
                style = DesignSystemThemeImpl.typeScale.textStrongM,
                color = DesignSystemThemeImpl.designSystemColor.contentDefaultLevel3,
            )
            Spacer(modifier = Modifier.size(8.dp))
            ArchiText(
                text = viewTypeVO.descriptionText,
                style = DesignSystemThemeImpl.typeScale.textRegularM,
                color = DesignSystemThemeImpl.designSystemColor.contentDefaultLevel1,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = viewTypeVO.endImage.imgUrl,
            imageLoader = imageLoader,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(viewTypeVO.endImage.size.width.dp)
                .background(Color(viewTypeVO.endImage.bgColor.toColorInt())),
        )
    }
}

@Composable
fun StartImageTitleDescActionIconView(viewTypeVO: StartImageTitleDescActionIconViewTypeVO) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.Green)
    ) {
        ArchiText(
            text = viewTypeVO.titleText,
            style = DesignSystemThemeImpl.typeScale.textStrongM,
            color = DesignSystemThemeImpl.designSystemColor.contentDefaultLevel3,
        )
        Spacer(modifier = Modifier.size(8.dp))
        ArchiText(
            text = viewTypeVO.descriptionText,
            style = DesignSystemThemeImpl.typeScale.textRegularM,
            color = DesignSystemThemeImpl.designSystemColor.contentDefaultLevel1,
        )
    }
}