package com.epmedu.animeal.foundation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Composable
fun SearchView(
    initialValue: String,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    var query by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initialValue))
    }

    TextField(
        value = query,
        onValueChange = { value ->
            query = value
            onValueChange(query)
        },
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            lineHeight = 20.sp
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.search_hint),
                style = TextStyle(
                    color = CustomColor.TrolleyGrey,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                ),
            )
        },
        trailingIcon = {
            SearchTrailingIcon(query) { value ->
                query = value
                onValueChange(value)
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            focusManager.clearFocus()
        }),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            cursorColor = CustomColor.SeaSerpent,
            trailingIconColor = Color.Black,
            backgroundColor = CustomColor.LynxWhite,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun SearchTrailingIcon(
    query: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    if (query != TextFieldValue()) {
        IconButton(onClick = {
            onValueChange(TextFieldValue())
        }) {
            Icon(
                Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier.size(18.dp),
                tint = CustomColor.TrolleyGrey
            )
        }
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(
                color = CustomColor.TrolleyGrey
            )
        )
    }
}

@AnimealPreview
@Composable
private fun SearchViewPreview() {
    AnimealTheme {
        SearchView(initialValue = "", onValueChange = {})
    }
}