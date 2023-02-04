package com.samuelav.presentation.features.advert

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samuelav.presentation.analytics.base.ScreenAnalytics
import org.koin.androidx.compose.get

@Composable
fun AdvertBanner(modifier: Modifier = Modifier) {
    val screenAnalytics: ScreenAnalytics = get()

    // TODO: Remove '/**/' after google-services.json file and ad id are added
    /*AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = context.getString(R.string.ad_id_banner)
                loadAd(AdRequest.Builder().build())
                adListener = object : AdListener() {
                    override fun onAdClicked() {
                        super.onAdClicked()
                        screenAnalytics.notifyAction(AdvertScreenAnalytics.Click)
                    }
                }
            }
        }
    )*/
}