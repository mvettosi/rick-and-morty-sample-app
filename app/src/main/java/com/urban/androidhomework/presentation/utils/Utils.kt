package com.urban.androidhomework.presentation.utils

import com.urban.androidhomework.domain.Character
import retrofit2.Response

class Utils {
    companion object {
        fun getName(characterResponse: Response<Character>): List<String> {
            val names = mutableListOf<String>()
            for (ch in characterResponse.body()!!.results) {
                names.add(ch.name)
            }

            return names
        }
    }
}