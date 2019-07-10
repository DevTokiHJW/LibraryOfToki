package com.devtokihjw.libraryoftoki.util

import android.content.Context
import android.util.TypedValue

fun Context.getDisplayMetrics() = resources.displayMetrics

fun Context.getDensity() = getDisplayMetrics().density

fun Context.getScaledDensity() = getDisplayMetrics().scaledDensity

fun Float.dp2px(context: Context) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.getDisplayMetrics()).toInt()

fun Float.sp2px(context: Context) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, context.getDisplayMetrics()).toInt()