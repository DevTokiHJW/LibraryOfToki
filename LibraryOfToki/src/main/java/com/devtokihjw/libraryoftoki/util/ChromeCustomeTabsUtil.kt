package com.devtokihjw.libraryoftoki.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION

const val STABLE_PACKAGE = "com.android.chrome"
const val BETA_PACKAGE = "com.chrome.beta"
const val DEV_PACKAGE = "com.chrome.dev"
const val LOCAL_PACKAGE = "com.google.android.apps.chrome"

var sPackageNameToUse: String? = null

fun Context.launchUrlOfChromeCustomeTabs(url: String, func: () -> Intent) {
    printLog("launchUrlOfChromeCustomeTabs", "Url = $url")
    val packageName = getPackageNameToUse()
    if (packageName.isNullOrEmpty()) {
        startActivity(func())
    } else {
        CustomTabsIntent.Builder().build().run {
            intent.setPackage(packageName)
            launchUrl(this@launchUrlOfChromeCustomeTabs, Uri.parse(url))
        }
    }
}

/**
 * Goes through all apps that handle VIEW intents and have a warmup service. Picks
 * the one chosen by the user if there is one, otherwise makes a best effort to return a
 * valid package name.
 *
 * This is <strong>not</strong> threadsafe.
 *
 * @param context {@link Context} to use for accessing {@link PackageManager}.
 * @return The package name recommended to use for connecting to custom tabs related components.
 */
private fun Context.getPackageNameToUse(): String? {
    val pm = packageManager
    // Get default VIEW intent handler.
    val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"))

    val packagesSupportingCustomTabs = pm.getCustomTabsPackages(activityIntent)
    val defaultViewHandlerPackageName = pm.resolveActivity(activityIntent, 0)?.activityInfo?.packageName

    // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
    // and service calls.
    if (packagesSupportingCustomTabs.isEmpty()) {
        sPackageNameToUse = null
    } else if (packagesSupportingCustomTabs.size == 1) {
        sPackageNameToUse = packagesSupportingCustomTabs[0]
    } else if (!defaultViewHandlerPackageName.isNullOrEmpty() && !hasSpecializedHandlerIntents(activityIntent, pm) && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
        sPackageNameToUse = defaultViewHandlerPackageName
    } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
        sPackageNameToUse = STABLE_PACKAGE
    } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
        sPackageNameToUse = BETA_PACKAGE
    } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
        sPackageNameToUse = DEV_PACKAGE
    } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
        sPackageNameToUse = LOCAL_PACKAGE
    }
    return sPackageNameToUse
}

private fun PackageManager.getCustomTabsPackages(mIntent: Intent): List<String> {
    // Get all apps that can handle VIEW intents.
    val resolvedActivityList = queryIntentActivities(mIntent, 0)
    val packagesSupportingCustomTabs = ArrayList<String>()
    for (info in resolvedActivityList) {
        val serviceIntent = Intent()
        serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
        serviceIntent.setPackage(info.activityInfo.packageName)
        // Check if this package also resolves the Custom Tabs service.
        if (resolveService(serviceIntent, 0) != null) {
            packagesSupportingCustomTabs.add(info.activityInfo.packageName)
        }
    }
    return packagesSupportingCustomTabs
}

/**
 * Used to check whether there is a specialized handler for a given intent.
 * @param intent The intent to check with.
 * @return Whether there is a specialized handler for the given intent.
 */
private fun hasSpecializedHandlerIntents(intent: Intent, mPackageManager: PackageManager): Boolean {
    try {
        val handlers = mPackageManager.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER)
        if (handlers == null || handlers.size == 0) {
            return false
        }
        for (resolveInfo in handlers) {
            val filter = resolveInfo.filter ?: continue
            if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
            if (resolveInfo.activityInfo == null) continue
            return true
        }
    } catch (e: RuntimeException) {
        printLog("ChromeCustomeTabsUtil", "RuntimeException = Runtime exception while getting specialized handlers")
    }

    return false
}