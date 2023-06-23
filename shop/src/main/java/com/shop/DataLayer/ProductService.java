package com.shop.DataLayer;

import com.shop.models.Product;
import com.shop.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Add new product
    public Product addProduct(Product product) throws Exception {
        logger.info("Adding NAME a new product: {}", product.getName());

        try {
            Product addedProduct = productRepository.save(product);
            logger.info("Product added successfully: {}", addedProduct.getName());
            return addedProduct;
        } catch (Exception e) {
            logger.error("Failed to add product: {}, ", product.getName() + e.getMessage());
            throw new Exception("Failed to add product: "+ e.getMessage());
        }
    }

    //Update new product
    public Product updateProduct(Product product) throws Exception {
        logger.info("Updating product with ID {}: {}", product.getProductId(), product.getName());
        try {
            Product updatedProduct = productRepository.save(product);
            logger.info("Product updated successfully for: {}", updatedProduct.getName());
            return updatedProduct;
        } catch (Exception e) {
            logger.error("Failed to update product with ID {}: {}", product.getName(), product, e);
            throw new Exception("Failed to update product: " + e.getMessage());
        }
    }

    public List<Product> getAllProducts( ) throws Exception {
        logger.info("getting All Products");
        try {
            List<Product> productList = (List<Product>) productRepository.findAll();
            logger.info("getting All Product successfully");
            return productList;
        } catch (Exception e) {
            logger.error("Failed to getting All Product: " + e.getMessage());
            throw new Exception("Failed to getting All Product: " + e.getMessage());
        }
    }

    public Product getProductById(int productId) throws Exception {
        logger.info("Getting Product with ID: " + productId);
        try {
            Product optionalProduct = productRepository.findById(productId);
            if (optionalProduct != null) {
                logger.info("Successfully retrieved Product with ID: " + productId);
                return optionalProduct;
            } else {
                throw new Exception("Product not found");
            }
        } catch (Exception e) {
            logger.error("Failed to get Product with ID: " + productId + ". " + e.getMessage());
            throw new Exception("Failed to get Product with ID: " + productId + ". " + e.getMessage());
        }
    }

    //Delete a product
    public String deleteProduct(int productId) throws Exception {
        logger.info("Deleting product with ID: {}", productId);
        try {
            Product product = productRepository.findById(productId);
            if (product!= null){
                productRepository.deleteById(productId);
                logger.info("Product deleted successfully with ID: {}", productId);
                String stringRet = "Product "+productId+" was deleted";
                return stringRet;
            }else {
                logger.info("Product with ID: {} not found", productId);
                String stringRet = "ProductID: "+productId+" not found";
                return stringRet;
            }
        } catch (Exception e) {
            logger.error("Failed to delete product with ID: {}", productId + e.getMessage());
            throw new Exception("Failed to delete product: " + e.getMessage());
        }
    }
/*
    public void deleteProductByName(String name) throws Exception {
        logger.info("Deleting product with ID: {}", productId);
        try {
            productRepository.(productId);
            logger.info("Product deleted successfully with ID: {}", productId);
        } catch (Exception e) {
            logger.error("Failed to delete product with ID: {}", productId, e);
            throw new Exception("Failed to delete product.", e);
        }
    }*/
}
