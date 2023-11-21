package com.example.roomdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProductRepository(private val productDao: ProductDao) {
    val searchResults = MutableLiveData<List<Product>>()
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertProduct(newProduct: Product) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.insertProduct(newProduct)
        }
    }

    fun deleteProduct(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.deleteProduct(name)
        }
    }

    fun findProduct(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindProduct(name).await()
        }
    }

    private fun asyncFindProduct(name: String): Deferred<List<Product>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async productDao.findProduct(name)
        }

}