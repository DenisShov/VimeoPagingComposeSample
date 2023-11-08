package com.dshovhenia.compose.playgroundapp.feature.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dshovhenia.compose.playgroundapp.R
import com.dshovhenia.compose.playgroundapp.feature.home.HomeViewModel

@Composable
fun SearchBar(viewModel: HomeViewModel) {
    val localFocusManager = LocalFocusManager.current
    var searchBarText by rememberSaveable { mutableStateOf("") }

    Card(
        elevation = 6.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        TextField(
            value = searchBarText,
            onValueChange = {
                searchBarText = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.search), color = Color.White)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, "", tint = Color.White)
            },
            trailingIcon = {
                if (searchBarText != "") {
                    IconButton(onClick = {
                        searchBarText = ""
                        viewModel.searchKeywordVideos("")
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            },
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            keyboardActions = KeyboardActions(onSearch = {
                if (searchBarText != "") {
                    viewModel.searchKeywordVideos(searchBarText)
                }
                localFocusManager.clearFocus()
            }),
            colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
            singleLine = true
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(viewModel = viewModel())
}
