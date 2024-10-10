package com.shopbackend.shoppingcard.controller;


import com.shopbackend.shoppingcard.Model.Product;
import com.shopbackend.shoppingcard.Request.AddProductRequest;
import com.shopbackend.shoppingcard.Request.ProductUpdateRequest;
import com.shopbackend.shoppingcard.Service.product.IProductService;
import com.shopbackend.shoppingcard.dto.ProductDto;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import com.shopbackend.shoppingcard.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

@GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse(convertedProducts, "Success"));

    }
@GetMapping("{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id)
    {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToProductDto(product);
            return ResponseEntity.ok(new ApiResponse(productDto, "Success"));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse>  addProduct(@RequestBody AddProductRequest product)
    {
        try {
            Product xproduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse(xproduct, "Success!"));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long productId)
    {
        try {
            Product updateProduct = productService.updateProduct(product, productId);
            return ResponseEntity.ok(new ApiResponse(updateProduct, "Success!"));
        } catch (ResourceNotFoundException e) {
           return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
@DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)
    {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse(productId, "Successfully Deleted!"));
        } catch (ResourceNotFoundException e) {
return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

@GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name)
    {

        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if(products.isEmpty())
            {
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No Products Found"));
        }
            return ResponseEntity.ok(new ApiResponse(convertedProducts, "Success"));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand)
    {

        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if(products.isEmpty())
            {
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No Products Found"));
            }
            return ResponseEntity.ok(new ApiResponse(convertedProducts, "Success"));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }



    }



    @GetMapping("/product/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name)
    {

        try {
            List<Product> products = productService.getProductsByName(name);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if(products.isEmpty())
            {
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No Products Found"));
            }
            return ResponseEntity.ok(new ApiResponse(convertedProducts, "Success"));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand)
    {

        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if(products.isEmpty())
            {
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No Products Found"));
            }
            return ResponseEntity.ok(new ApiResponse(convertedProducts, "Success"));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category)
    {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty())
            {
                return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, "No Products Found"));
            }
            return ResponseEntity.ok(new ApiResponse(products, "Success"));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/product/count/by-brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name)
    {
        try
        {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse(productCount, "Success"));
        }
        catch (Exception e)
        {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}


