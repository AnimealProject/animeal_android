package com.epmedu.animeal.signup.entercode.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.epmedu.animeal.extensions.retrieveParcelable
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

@SuppressLint("WrongConstant")
@Composable
fun SMSReader(onSMS: (String) -> Unit) {
    val context = LocalContext.current

    val smsReceiverLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val message = result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                message?.let { onSMS(it) }
            }
        }
    )

    val smsVerificationReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                    val extras = intent.extras
                    val smsRetrieverStatus =
                        extras?.retrieveParcelable<Status>(SmsRetriever.EXTRA_STATUS)

                    if (smsRetrieverStatus?.statusCode == CommonStatusCodes.SUCCESS) {
                        val consentIntent =
                            extras.retrieveParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            smsReceiverLauncher.launch(consentIntent)
                        } catch (e: ActivityNotFoundException) {
                            Log.e("SMSReader", e.message ?: e.javaClass.name)
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        SmsRetriever.getClient(context).startSmsUserConsent(null)
        ContextCompat.registerReceiver(
            context,
            smsVerificationReceiver,
            IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),
            ContextCompat.RECEIVER_EXPORTED
        )

        onDispose { context.unregisterReceiver(smsVerificationReceiver) }
    }
}