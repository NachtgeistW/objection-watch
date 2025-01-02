package com.nachtgeistw.objectionwatch.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import com.nachtgeistw.objectionwatch.R

@Preview
@Composable
public fun ObjectionScreen(
    modifier: Modifier = Modifier,
    imageId: Int = R.drawable.etc00a,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Image(
            modifier = Modifier.matchParentSize().background(Color.Transparent),
            bitmap = ImageBitmap.imageResource(imageId),
            contentDescription = "play objection sound"
        )
    }
}