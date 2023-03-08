package com.epmedu.animeal.tabs.search.presentation.search

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    initialValue: String = EMPTY_STRING,
    onValueChanged: (TextFieldValue) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    var query by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(initialValue))
    }

    TextField(
        value = query,
        onValueChange = { value ->
            query = value
            onValueChanged(query)
        },
        //TODO Inner padding break everything with designed 40.dp height, mb migrate to BasicTextField
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
        textStyle = TextStyle(
            color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Light, lineHeight = 20.sp
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
            if (query != TextFieldValue()) {
                IconButton(onClick = {
                    query = TextFieldValue()
                    // Remove text from TextField when you press the 'X' icon
                }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = EMPTY_STRING,
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

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    SearchView()
}