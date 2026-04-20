package dev.eliaschen.blurbackdrop

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AvatarCutThrough(modifier: Modifier = Modifier) {
    val graphicsLayer = rememberGraphicsLayer()
    var boxPos by remember { mutableStateOf<Offset?>(null) }
    var movePos by remember { mutableStateOf(Offset.Zero) }
    var resize by remember { mutableFloatStateOf(1f) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                }
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        movePos += pan
                        resize *= zoom
                    }
                }
                .graphicsLayer {
                    translationX = movePos.x
                    translationY = movePos.y
                    scaleX = resize
                    scaleY = resize
                }) {
            Image(
                painter = painterResource(R.drawable.eliaschen_500),
                contentDescription = null, modifier = Modifier.fillMaxSize()
            )
        }
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
                    .onGloballyPositioned { boxPos = it.positionInWindow() }
                    //                            .border(1.dp, Color.Gray.copy(0.5f))
                    .blur(30.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .drawBehind {
                        boxPos?.let { offset ->
                            drawIntoCanvas { canvas ->
                                canvas.save()
                                canvas.translate(-offset.x, -offset.y)
                                drawLayer(graphicsLayer)
                                canvas.restore()
                            }
                        }
                    }
            )
            Text("Hello", color = Color.White)
        }
    }

}