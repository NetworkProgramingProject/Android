package com.example.socketprogramming.ui.product

import android.graphics.Bitmap
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
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

    private var _productImg = MutableLiveData<Uri>()
    val productImg: LiveData<Uri> = _productImg

    private var _imgFile = MutableLiveData<String>()
    val imgFile: LiveData<String> = _imgFile

    private var _registerProduct = MutableLiveData<Boolean>()
    val registerProduct: LiveData<Boolean> = _registerProduct


    /** 생성자 */
    init {
        _checking.value = false
        _clickImg.value = false
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
                val bitmap = MediaStore.Images.Media.getBitmap(
                    SocketApplication.getGlobalAppApplication().contentResolver,
                    _productImg.value!!
                )

                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val imageFileName = "${timeStamp}_${_productTitle.value!!}.png"

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                val fileReqBody = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
                val part = MultipartBody.Part.createFormData("img", imageFileName, fileReqBody)

                socketRepository.postRegisterProduct(
                    contentType = "multipart/form-data",
                    title = _productTitle.value!!,
                    desc = _productDesc.value!!,
                    min_price = _minPrice.value!!,
                    img = part,
                    onSuccess = {
                    if (it.success) {
                        Timber.e("통신 성공")
                    }
                    }, onFailure = {

                    }
                )

            }
        }
    }

    fun getImage(uri: Uri) {
        _productImg.value = uri
    }


    /** UI 의 onDestroy 개념으로 생각하면 편할듯 */
    override fun onCleared() {
        super.onCleared()
    }
}
