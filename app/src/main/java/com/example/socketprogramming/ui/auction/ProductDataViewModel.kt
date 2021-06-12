package com.example.socketprogramming.ui.auction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.perfumeproject.ui.base.BaseViewModel
import com.example.socketprogramming.data.response.ProductData
import com.example.socketprogramming.network.SocketRepository
import timber.log.Timber

abstract class ProductDataViewModel constructor(
    private val socketRepository: SocketRepository
) : BaseViewModel(socketRepository) {
    /** 검색 결과 list */

    protected val _results = MutableLiveData<List<ProductData>>(listOf())
    open val results : LiveData<List<ProductData>> = _results

    protected val _productSize = MutableLiveData<Int>()
    open val productSize : LiveData<Int> = _productSize

    /** productData 아이템뷰를 클릭시 동작하는 로직 */
    abstract fun productItemClick(productData: ProductData)


    /** [_results] 내 특정 아이템을 추가한다. */
    fun addProductData(productData: ProductData) {
        val results: MutableList<ProductData> = _results.value?.toMutableList() ?: mutableListOf()
        results.add(productData)
        _results.value = results
        _productSize.value = results.size
    }

    /** [_results] 내 특정 아이템을 [productData] 변경한다. */
    fun replaceProductData(productData: ProductData) {
        val results: MutableList<ProductData> = _results.value?.toMutableList() ?: return

        val prevResult = (results.filter { productData.title == it.title }).firstOrNull()

        prevResult?.let {
            val replaceIndex = results.indexOf(it)

            if (replaceIndex == -1) {
                Timber.i("$it isn't exist")
                return
            } else {
                results.removeAt(replaceIndex)
                results.add(replaceIndex, productData)
                _results.value = results
                _productSize.value = results.size
            }
        }
    }

    /** [_results] 내 특정 아이템을 삭제한다. */
    fun deleteProductData(productData: ProductData) {
        val results: MutableList<ProductData> = _results.value?.toMutableList() ?: return

        val deleteIndex = results.indexOf(productData)

        if (deleteIndex == -1) {
            Timber.i("$productData isn't exist")
            return
        } else {
            results.removeAt(deleteIndex)
            _results.value = results
            _productSize.value = results.size
        }
    }


}
