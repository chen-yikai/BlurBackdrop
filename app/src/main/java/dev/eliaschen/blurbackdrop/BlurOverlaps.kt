package dev.eliaschen.blurbackdrop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalWithComputedDefaultOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BlurOverlaps(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(30f))
                .clipToBounds()
        ) {
            Image(
                painter = painterResource(R.drawable.eliaschen_500),
                contentDescription = null, modifier = Modifier
                    .size(300.dp)
            )
            Image(
                painter = painterResource(R.drawable.eliaschen_500),
                contentDescription = null, modifier = Modifier
                    .size(300.dp)
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .blur(50.dp)
                    .drawWithContent {
                        val width = size.width
                        val height = size.height
                        drawContent()
                        val padding = 130f
                        drawRoundRect(
                            Color.Black,
                            Offset(padding, padding),
                            size = Size(width - padding * 2, height - padding * 2),
                            blendMode = BlendMode.Clear
                        )
                    }
            )
        }
    }
}
