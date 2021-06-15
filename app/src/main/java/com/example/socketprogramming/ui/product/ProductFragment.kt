package com.example.socketprogramming.ui.product

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.perfumeproject.ui.base.BaseFragment
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.SocketApplication
import com.example.socketprogramming.databinding.ProductFragmentBinding
import com.example.socketprogramming.ui.auction.AuctionFragment
import com.example.socketprogramming.util.toast
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class ProductFragment : BaseFragment<ProductFragmentBinding>(R.layout.product_fragment) {

    override val viewModel: ProductViewModel by viewModels<ProductViewModel>()
    private lateinit var lifecycleOwner : LifecycleOwner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.vm, viewModel)
        lifecycleOwner = this@ProductFragment.viewLifecycleOwner

        binding.apply {
            lifecycleOwner = this@ProductFragment.viewLifecycleOwner
        }

        viewModel.clickImg.observe(lifecycleOwner, Observer {
            if (it) {
                //갤러리 퍼미션
                if (!allStoragePermissionsGranted()) {
                    ActivityCompat.requestPermissions(
                            this.requireActivity(), REQUIRED_STORAGE_PERMISSIONS, REQUEST_CODE_STORAGE_PERMISSIONS)
                } else {
                    getImage()
                }
            }
        })

        viewModel.successRegister.observe(lifecycleOwner, Observer {
            if (it) {
                toast("상품이 등록되었습니다!")
                val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                val auctionFragment = AuctionFragment()
                transaction.replace(R.id.fl_main, auctionFragment)
                transaction.commit()
            }
        })


    }

    private fun getImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, GET_GALLERY_IMG)
    }

    @Override
    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GET_GALLERY_IMG && resultCode == RESULT_OK && data != null && data!!.data != null ) {
            val selectedImage = data!!.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(SocketApplication.appContext!!.contentResolver, selectedImage)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream)
            val byteArray = stream.toByteArray()

            val fileReqBody = RequestBody.create("image/png".toMediaTypeOrNull(), byteArray)
            val fileName = selectedImage.path!!
            val part = MultipartBody.Part.createFormData("img", fileName, fileReqBody)
            viewModel.getImage(part)
            Glide.with(this).load(selectedImage).into(binding.imgProductImg)
        }
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSIONS) {
            if(!allStoragePermissionsGranted()){
                toast("갤러리 접근 권한을 허용해주세요 :) ")
            } else {
                getImage()
            }
        }
    }

    private fun allStoragePermissionsGranted() = REQUIRED_STORAGE_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                SocketApplication.appContext!!, it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSIONS = 11
        private const val GET_GALLERY_IMG = 200
        private val REQUIRED_STORAGE_PERMISSIONS = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}