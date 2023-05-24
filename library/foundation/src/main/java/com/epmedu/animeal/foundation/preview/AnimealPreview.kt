package com.epmedu.animeal.foundation.preview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, device = Devices.PIXEL_2)
annotation class AnimealPreview
