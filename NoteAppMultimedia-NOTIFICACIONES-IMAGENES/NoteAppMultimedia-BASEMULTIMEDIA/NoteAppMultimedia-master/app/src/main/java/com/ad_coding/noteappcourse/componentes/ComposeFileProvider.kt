package com.ad_coding.noteappcourse.componentes

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.ad_coding.noteappcourse.R
import java.io.File

class ComposeFileProvider : FileProvider(
    R.xml.filepath
){
    companion object {
        fun getImageUri(context: Context): Uri {
            // 1
            Log.d("CREARARCHIVO","------------")
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            // 2

            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            // 3

            val authority = context.packageName + ".fileprovider"
            // 4

            return getUriForFile(
                context,
                authority,
                file,
            )
            //Uri.fromFile(file)
        }
    }
}