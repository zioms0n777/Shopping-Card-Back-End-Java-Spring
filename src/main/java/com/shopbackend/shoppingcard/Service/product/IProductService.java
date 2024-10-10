package com.shopbackend.shoppingcard.Service.product;

import com.shopbackend.shoppingcard.Model.Product;
import com.shopbackend.shoppingcard.Request.AddProductRequest;
import com.shopbackend.shoppingcard.Request.ProductUpdateRequest;
import com.shopbackend.shoppingcard.dto.ProductDto;

import java.util.List;

public interface IProductService {


    Product addProduct(AddProductRequest request);

    Product getProductById(Long id);
    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);


    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToProductDto(Product product);
}
