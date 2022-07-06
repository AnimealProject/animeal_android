package com.epmedu.animeal.foundation.input

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.CustomColor.CursorColor
import com.epmedu.animeal.base.theme.CustomColor.DarkerGrey
import com.epmedu.animeal.base.theme.CustomColor.LynxWhite

@Composable
fun TextInputField(
    modifier: Modifier,
    title: String,
    hint: String,
    onValueChange: (String) -> Unit,
    value: String
) {

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            modifier = Modifier
                .padding(bottom = 2.dp),
            style = TextStyle(
                color = MaterialTheme.colors.onSurface,
                fontSize = 14.sp
            )
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = LynxWhite,
                    shape = RoundedCornerShape(12.dp)
                ),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                backgroundColor = Color.Transparent,
                cursorColor = CursorColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = hint,
                    fontSize = 16.sp,
                    color = DarkerGrey
                )
            },
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TextInputFieldPreview() {
    AnimealTheme {
        Surface {
            var name by remember { mutableStateOf("") }

            TextInputField(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Name",
                hint = "Enter your name",
                onValueChange = { name = it },
                value = name
            )
        }
    }
}
