package com.dshovhenia.compose.playgroundapp.feature.details.components

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.data.local.db.model.comment.RelationsComment
import com.dshovhenia.compose.playgroundapp.feature.theme.ComposePlaygroundAppTheme
import com.dshovhenia.compose.playgroundapp.core.utils.TextUtil
import com.dshovhenia.compose.playgroundapp.core.utils.getRelationsComment
import java.util.Locale

@Composable
fun CommentItem(comment: RelationsComment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
    ) {
        @Suppress("IMPLICIT_CAST_TO_ANY") val authorPhotoUrl =
            if (comment.relationsUser.pictureSizes.size >= 2) {
                comment.relationsUser.pictureSizes[1].link
            } else {
                ColorDrawable(ContextCompat.getColor(LocalContext.current, R.color.darkLightGray))
            }
        AsyncImage(
            model = authorPhotoUrl,
            contentDescription = stringResource(R.string.image_description_user_image),
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = comment.relationsUser.user.name,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    color = colorResource(android.R.color.black),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                val commentAge = comment.comment.created_on?.let {
                    String.format(
                        Locale.getDefault(), " ⋅ %s", TextUtil.dateCreatedToTimePassed(it)
                    )
                } ?: ""
                Text(
                    text = commentAge,
                    modifier = Modifier.wrapContentWidth(),
                    color = colorResource(android.R.color.black),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = comment.comment.text,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 2.dp, end = 8.dp),
                color = colorResource(android.R.color.black)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentItemPreview() {
    ComposePlaygroundAppTheme {
        CommentItem(comment = getRelationsComment())
    }
}

