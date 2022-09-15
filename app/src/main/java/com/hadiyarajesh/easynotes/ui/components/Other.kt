package com.hadiyarajesh.easynotes.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.transform.Transformation
import com.hadiyarajesh.easynotes.R

fun Modifier.smallIconSize() = this.size(20.dp)
fun Modifier.iconSize() = this.size(24.dp)
fun Modifier.biggerIconSize() = this.size(28.dp)
fun Modifier.rowHeight() = this.height(50.dp)
fun Modifier.smallBoxModifier() = this
    .size(90.dp)
    .clip(RoundedCornerShape(8.dp))
    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))

/**
 * Create a [Spacer] of given width in dp
 */
@Composable
fun HorizontalSpacer(size: Int) = Spacer(modifier = Modifier.width(size.dp))

/**
 * Create a [Spacer] of given height in dp
 */
@Composable
fun VerticalSpacer(size: Int) = Spacer(modifier = Modifier.height(size.dp))

@Composable
fun ImageItem(
    modifier: Modifier = Modifier,
    data: Any?,
    crossfadeValue: Int = 300,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    transformation: Transformation? = null,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(crossfadeValue)
            .transformations(transformation?.let { listOf(transformation) } ?: emptyList())
            .build(),
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.placeholder),
        contentScale = contentScale
    )
}

@Composable
fun SubComposeImageItem(
    modifier: Modifier = Modifier,
    data: Any?,
    crossfadeValue: Int = 300,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    transformation: Transformation? = null,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(crossfadeValue)
            .transformations(transformation?.let { listOf(transformation) } ?: emptyList())
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        loading = { LoadingProgressBar() }
    )
}

@Composable
fun LoadingProgressBar(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = 4.dp
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .align(Alignment.Center),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

@Composable
fun RetryItem(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(onClick = onRetryClick) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.error,
    style: TextStyle = MaterialTheme.typography.caption
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = style,
    )
}

@Composable
fun EmptyViewWithImage(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes image: Int?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        image?.let {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        VerticalSpacer(size = 8)
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun EmptyViewWithText(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.SemiBold
        )
    }
}
