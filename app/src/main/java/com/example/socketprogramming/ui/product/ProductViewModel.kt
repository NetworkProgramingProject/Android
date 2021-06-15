package com.example.socketprogramming.ui.product

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.SocketApplication
import com.example.socketprogramming.network.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val socketRepository: SocketRepository,
) : BaseViewModel(socketRepository) {

    private var _productTitle = MutableLiveData<String>()
    val productTitle: LiveData<String> = _productTitle

    private var _productDesc = MutableLiveData<String>()
    val productDesc: LiveData<String> = _productDesc

    private var _minPrice = MutableLiveData<Int>()
    val minPrice: LiveData<Int> = _minPrice

    private var _checking = MutableLiveData<Boolean>()
    val checking: LiveData<Boolean> = _checking

    private var _clickImg = MutableLiveData<Boolean>()
    val clickImg: LiveData<Boolean> = _clickImg

    private var _productImg = MutableLiveData<MultipartBody.Part>()
    val productImg: LiveData<MultipartBody.Part> = _productImg

    private var _imgFile = MutableLiveData<String>()
    val imgFile: LiveData<String> = _imgFile

    private var _registerProduct = MutableLiveData<Boolean>()
    val registerProduct: LiveData<Boolean> = _registerProduct

    private var _successRegister = MutableLiveData<Boolean>()
    val successRegister: LiveData<Boolean> = _successRegister


    /** 생성자 */
    init {
        _checking.value = false
        _clickImg.value = false
        _successRegister.value = false
    }

    val productTitleCheck = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!p0.toString().isNullOrEmpty()) {
                _productTitle.value = p0.toString()
            } else {
                _productTitle.value = ""
            }
            checkProduct()

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    val productDescCheck = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!p0.toString().isNullOrEmpty()) {
                _productDesc.value = p0.toString()
            } else {
                _productDesc.value = ""
            }
            checkProduct()

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    val productPriceCheck = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!p0.toString().isNullOrEmpty()) {
                _minPrice.value = Integer.parseInt(p0.toString()!!)
            } else {
                _minPrice.value = 0
            }
            checkProduct()

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    fun clickImage() {
        _clickImg.value = true
    }

    private fun checkProduct() {
        _checking.value = ! _productTitle.value.isNullOrEmpty() && !_productDesc.value.isNullOrEmpty() && _minPrice.value != 0
    }

    fun registerProduct() {
        if(_checking.value!!) {
            if (_productImg.value != null) {

                _registerProduct.value = true
                val map = mutableMapOf<String, RequestBody>()
                map["title"] = stringToRequestBody(_productTitle.value!!)
                map["desc"] = stringToRequestBody(_productDesc.value!!)
                map["min_price"] = stringToRequestBody("${_minPrice.value!!}")


                socketRepository.postRegisterProduct(
                    map = map,
                    img = _productImg.value!!,
                    onSuccess = {
                    if (it.success) {
                        Timber.e("데이터 전달 통신 성공")
                        _successRegister.value = true
                    }
                    }, onFailure = {

                    }
                )

            }
        }
    }

    private fun stringToRequestBody(str : String) : RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), str)
    }

    fun getImage(multipartBody: MultipartBody.Part) {
        _productImg.value = multipartBody
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
