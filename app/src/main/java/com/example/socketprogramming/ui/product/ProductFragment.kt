package com.example.socketprogramming.ui.product

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.perfumeproject.ui.base.BaseFragment
import com.example.socketprogramming.BR
import com.example.socketprogramming.R
import com.example.socketprogramming.SocketApplication
import com.example.socketprogramming.databinding.MyPageFragmentBinding
import com.example.socketprogramming.databinding.ProductFragmentBinding
import com.example.socketprogramming.ui.login.LoginActivity
import com.example.socketprogramming.ui.mypage.MyPageViewModel
import com.example.socketprogramming.util.startActivity
import com.example.socketprogramming.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

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
            if(it) {
                //갤러리 퍼미션
                if (!allStoragePermissionsGranted()){
                    ActivityCompat.requestPermissions(
                        this.requireActivity(), REQUIRED_STORAGE_PERMISSIONS, REQUEST_CODE_STORAGE_PERMISSIONS)
                } else {
                    getImage()
                }
            }
        })

    }

    private fun getImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
        startActivityForResult(intent,GET_GALLERY_IMG)
    }

    @Override
    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GET_GALLERY_IMG && resultCode == RESULT_OK && data != null && data!!.data != null ) {
            val selectedImage = data!!.data!!
            val file = File(data.data!!.path)
            viewModel.getImage(file)
            Glide.with(this).load(selectedImage).into(binding.imgProductImg)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
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
            SocketApplication.appContext!!, it ) == PackageManager.PERMISSION_GRANTED
    }




    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSIONS = 11
        private const val GET_GALLERY_IMG = 200
        private val REQUIRED_STORAGE_PERMISSIONS = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }



}