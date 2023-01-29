package com.mc.vengateshm.expensetracker.common

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun Color.Companion.random():Color{
    return Color(Random.nextLong(0XFFFFFFFF))
}