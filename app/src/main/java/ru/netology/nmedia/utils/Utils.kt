package ru.netology.nmedia.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {
    companion object {
        fun numToPostfix(num: Int): String = when (num) {
            in 0..999 -> num.toString()
            in 1_000..9_999 -> if (num.toString()[1] == '0') "${num.toString()[0]}K" else "${num.toString()[0]},${num.toString()[1]}K"
            in 10_000..999_999 -> "${num.toString().dropLast(3)}K"
            else -> if (num.toString()[1] == '0') "${num.toString()[0]}M" else "${num.toString()[0]},${num.toString()[1]}M"
        }

        fun hideKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}