package fastcompus.part1.chapter8

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fastcompus.part1.chapter8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val imageLoadLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
        updateImage(uriList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loagImageButton.setOnClickListener {
            checkPermission()
        }
    }

    //권한에 필요한 요청값
    companion object {
        const val READ_MEDIA_IMAGES = 100
    }

    //이미지에 대한 권한요청
    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED -> {
                loadImage()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES) -> {
                showPermissionDialog()
            }
            else -> {
                requestReadExternalImage()
            }
        }
    }

    //이미지 가져오기(image 타입으로 구성된 파일만 불러오기)
    private fun loadImage() {
        imageLoadLauncher.launch("image/*")
    }

    //이미지 권한에 대한 다이얼로그 띄우기
    private fun showPermissionDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("이미지를 가져오기 위한 접근권한을 허용해주세요")
            setNegativeButton("불가", null)
            setPositiveButton("허용") { _, _ ->
                requestReadExternalImage()
            }
        }.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            READ_MEDIA_IMAGES -> {
                if(grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    loadImage()
                }
            }
        }
    }

    //이미지 권한허용에 대한 설정
    private fun requestReadExternalImage() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), 100)
    }

    //선택한 이미지 화면에 띄우기
    private fun updateImage(uriLis: List<Uri>) {
        Log.d("updatImage", "$uriLis")
    }
}