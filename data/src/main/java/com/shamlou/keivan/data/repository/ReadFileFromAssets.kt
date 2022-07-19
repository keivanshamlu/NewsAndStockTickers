package com.shamlou.keivan.data.repository

import android.content.Context

/**
 * reads file from assets and
 * returns string version of file
 */
class ReadFileFromAssets(private val applicationContext: Context) {

    // gets a file name and will read it from
    // asset and then give back the result as string
    fun readFile(fileName: String): String {
        return applicationContext.assets.open(fileName).run {
            ByteArray(available()).let {
                read(it)
                close()
                String(it, charset("UTF-8"))
            }
        }
    }
}