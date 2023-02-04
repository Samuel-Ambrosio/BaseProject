package com.samuelav.presentation.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.samuelav.presentation.common.R

fun Context.sendEmail(
    to: String = getString(R.string.app_email_contact),
    subject: String,
    onFailedSendEmail: () -> Unit,
) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            onFailedSendEmail()
        }
    } catch (ex: Exception) {
        onFailedSendEmail()
    }
}

fun Context.shareText(title: String, sharedText: String) {
    val shareIntent = createShareIntent(sharedText)
    startActivity(createChooserIntent(shareIntent, title))
}

private fun createShareIntent(text: String): Intent =
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }

private fun createChooserIntent(actionIntent: Intent, title: String): Intent =
    Intent.createChooser(actionIntent, title)