package com.dshovhenia.vimeo.paging.compose.sample.feature.details.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.dshovhenia.vimeo.paging.compose.sample.R

const val DEFAULT_MINIMUM_TEXT_LINE = 3

@Composable
fun ExpandableText(
        modifier: Modifier = Modifier,
        textModifier: Modifier = Modifier,
        style: TextStyle = LocalTextStyle.current,
        fontStyle: FontStyle? = null,
        text: String,
        collapsedMaxLine: Int = DEFAULT_MINIMUM_TEXT_LINE,
        showMoreText: String = stringResource(id = R.string.show_more),
        showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
        showLessText: String = "  ${stringResource(id = R.string.show_less)}",
        showLessStyle: SpanStyle = showMoreStyle,
        textAlign: TextAlign? = null
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var clickable by rememberSaveable { mutableStateOf(false) }
    var lastCharIndex by rememberSaveable { mutableStateOf(0) }
    Box(modifier = Modifier
            .clickable(clickable) {
                isExpanded = !isExpanded
            }
            .then(modifier)) {
        Text(
                modifier = textModifier
                        .fillMaxWidth()
                        .animateContentSize(),
                text = buildAnnotatedString {
                    if (clickable) {
                        if (isExpanded) {
                            append(text)
                            withStyle(style = showLessStyle) { append(showLessText) }
                        } else {
                            val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                                    .dropLast(showMoreText.length)
                                    .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                            append(adjustText)
                            withStyle(style = showMoreStyle) { append(showMoreText) }
                        }
                    } else {
                        append(text)
                    }
                },
                maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
                fontStyle = fontStyle,
                onTextLayout = { textLayoutResult ->
                    if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                        clickable = true
                        lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                    }
                },
                style = style,
                textAlign = textAlign
        )
    }
}
