package com.pabji.myfridge.model.database

import com.pabji.data.datasources.LocalDatasource
import com.pabji.domain.DetailError
import com.pabji.domain.Either
import com.pabji.domain.Product
import com.pabji.myfridge.model.database.entities.toProduct
import com.pabji.myfridge.model.database.entities.toProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(database: RoomDatabase) : LocalDatasource {

    private val productDao = database.productDao()

    override suspend fun getProductByBarcode(barcode: String?) =
        withContext(Dispatchers.IO) {
            productDao.getProductByBarcode(barcode ?: "")?.run {
                Either.Right(toProduct())
            } ?: Either.Left(DetailError)
        }

    override suspend fun getProductList() =
        withContext(Dispatchers.IO) { productDao.getAll().map { it.toProduct() } }

    override suspend fun saveProduct(product: Product) =
        withContext(Dispatchers.IO) {
            productDao.insert(product.toProductEntity())
        }

    override suspend fun removeProduct(product: Product) =
        withContext(Dispatchers.IO) {
            productDao.remove(product.toProductEntity())
        }

    override suspend fun getProductsByTerm(searchTerm: String) =
        withContext(Dispatchers.IO) {
            productDao.getProductsByTerm(searchTerm).map { it.toProduct() }
        }
}
