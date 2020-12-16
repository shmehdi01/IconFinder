package syed.iconfinder.ui

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import syed.iconfinder.R
import syed.iconfinder.base.BaseActivity
import syed.iconfinder.databinding.ActivityFullScreenViewBinding
import syed.iconfinder.model.Category
import syed.iconfinder.model.Format
import syed.iconfinder.model.RasterSize
import syed.iconfinder.utils.Constants
import syed.iconfinder.utils.checkPermissionUtil
import syed.iconfinder.utils.loadImage
import syed.iconfinder.utils.showToast

class FullScreenViewActivity : BaseActivity() {

    private lateinit var format: Format
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFullScreenViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rasterSize = intent.getParcelableArrayListExtra<RasterSize>(Constants.KEY_RASTER_SIZE)
         category = intent.getStringExtra(Constants.KEY_CATEGORY)!!

        rasterSize?.let {
            format = it[it.size -1].formats[0]
            binding.ivIcon.loadImage(format.preview_url)
        }

        binding.btnDownload.setOnClickListener {
            checkPermissionAndDownload()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 100) {
            checkPermissionAndDownload()
        }
    }

    private fun checkPermissionAndDownload() {
        checkPermissionUtil(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,getSharedPreferences("icf",0)) {
            ask, pDenied ->

            when {
                pDenied -> {
                    showToast("You have permanently denied the permission, please go to setting and enable")
                }
                ask -> {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        100
                    )
                }
                else -> {
                    startDownload()
                }
            }
        }
    }

    private fun startDownload() {
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(format.preview_url))
        request.setTitle("Downloading $category")
        request.setDescription("Downloading")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${category}_${System.currentTimeMillis()}.${format.format}")
        downloadManager.enqueue(request)
    }
}