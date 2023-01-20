package com.epmedu.animeal.extensions

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.getSystemService
import com.epmedu.animeal.common.constants.Links.ANIMEAL_WEB_LINK
import com.epmedu.animeal.common.constants.Links.FACEBOOK_APP_LINK
import com.epmedu.animeal.common.constants.Links.FACEBOOK_WEB_LINK
import com.epmedu.animeal.common.constants.Links.INSTAGRAM_WEB_LINK
import com.epmedu.animeal.common.constants.Links.LINKEDIN_WEB_LINK
import com.epmedu.animeal.resources.R

private const val URI_SCHEME = "package"

fun Context.launchAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts(URI_SCHEME, packageName, null)
    }
    startActivity(intent)
}

// FLAG_ACTIVITY_NEW_TASK is required when we're starting activity from app context
fun Context.launchGpsSettings() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

fun Context.drawableCompat(id: Int) = requireNotNull(AppCompatResources.getDrawable(this, id)) {
    "Drawable with $id is null"
}

fun Context.copyText(
    text: String,
    @StringRes label: Int = R.string.text_copy_label,
    @StringRes toastText: Int?
) {
    val clipboard = getSystemService<ClipboardManager>()
    val clip = ClipData.newPlainText(getString(label), text)
    clipboard?.setPrimaryClip(clip)
    toastText?.let {
        Toast
            .makeText(this, getString(toastText), Toast.LENGTH_SHORT)
            .show()
    }
}

fun Context.startAppOrBrowser(appIntent: Intent, webIntent: Intent) {
    try {
        startActivity(appIntent)
    } catch (e: ActivityNotFoundException) {
        Log.e("tag", e.message.toString())
        startActivity(webIntent)
    }
}

fun Context.openFacebook() {
    val facebookAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_APP_LINK))
    val facebookWebIntent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_WEB_LINK))
    startAppOrBrowser(facebookAppIntent, facebookWebIntent)
}

fun Context.openInstagram() {
    val instagramAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_WEB_LINK))
        .setPackage("com.instagram.android")
    val instagramWebIntent = Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_WEB_LINK))
    startAppOrBrowser(instagramAppIntent, instagramWebIntent)
}

fun Context.openLinkedin() {
    val linkedinWebIntent = Intent(Intent.ACTION_VIEW, Uri.parse(LINKEDIN_WEB_LINK))
    startActivity(linkedinWebIntent)
}

fun Context.openAnimealWebsite() {
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(ANIMEAL_WEB_LINK))
    startActivity(webIntent)
}

inline val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager
