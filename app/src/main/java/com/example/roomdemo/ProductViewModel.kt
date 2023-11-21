package com.example.roomdemo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel(application: Application) : ViewModel() {
    private val productRepository: ProductRepository
    val allProducts: LiveData<List<Product>>
    val searchResults: MutableLiveData<List<Product>>

    init {
        val db = ProductRoomDatabase.getInstance(application)
        val productDao = db.productDao()
        productRepository = ProductRepository(productDao)
        allProducts = productRepository.allProducts
        searchResults = productRepository.searchResults
    }

    fun insertProduct(product: Product) {
        productRepository.insertProduct(product)
    }

    fun findProduct(name: String) {
        productRepository.findProduct(name)
    }

    fun deleteProduct(name: String) {
        productRepository.deleteProduct(name)
    }
}