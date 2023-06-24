package com.shop.entities;

import com.shop.DataLayer.ProductService;
import com.shop.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            Product productAdd = productService.addProduct(product);
            if (productAdd != null){
                return ResponseEntity.ok(productAdd.getName()+" added successfully.");
            }else {
                return sendError("Product NOT saved");
            }
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }
    @GetMapping("/getAllProduct")
    public ResponseEntity getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        }catch (Exception e){
            return sendError(e.getMessage());
        }
    }
    /*
    //get one
    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    */
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable int productId, @RequestBody Product updatedProduct) {
        try {
            updatedProduct.setProductID(productId);
            Product product = productService.updateProduct(updatedProduct);
            if (product != null) {
                return ResponseEntity.ok("Product updated successfully: "+updatedProduct.getName());
            } else {
                return sendError("Product NOT updated");
            }
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }


    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        try {
            String retString = productService.deleteProduct(productId);
            return ResponseEntity.ok(retString);
        } catch (Exception e) {
            return sendError(e.getMessage());
        }
    }
    //function to send error back to client
    public ResponseEntity<String> sendError(String str) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(str);
    }
}
