package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.ProductDetailsDto;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ProductDetailsNotFoundException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        logger.info("Adding product: {}", productDto);

        Optional<Product> existingProductOpt = productRepository.findBySku(productDto.sku());
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            existingProduct.setQuantity(existingProduct.getQuantity() + 1);
            productRepository.save(existingProduct);
            return ProductMapper.toDto(existingProduct);
        } else {
            Product newProduct = ProductMapper.toEntity(productDto);
            Product savedProduct = productRepository.save(newProduct);
            return ProductMapper.toDto(savedProduct);
        }
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        logger.info("Updating product with id {}: {}", productId, productDto);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        product.setQuantity(productDto.quantity());
        product.setPriceUnit(productDto.priceUnit());

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toDto(updatedProduct);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = findProductById(productId);
        logger.info("Retrieved product with id: {}", productId);
        return ProductMapper.toDto(product);
    }

    @Override
    public ProductDetailsDto getProductDetailsById(Long productId) {
        logger.info("Getting product details by id: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductDetailsNotFoundException("Failed to get product details by id: " + productId));

        return ProductMapper.toProductDetailsDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        logger.info("Searching products with keyword: {}", keyword);
        return productRepository.findByProductTitleContaining(keyword)
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> filterProductsByCategory(Long categoryId) {
        logger.info("Filtering products by category id: {}", categoryId);
        List<Product> products = productRepository.findByCategoryCategoryId(categoryId);
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public ProductDto updateStock(Long productId, int quantity) {
        logger.info("Updating stock for product id {}: {}", productId, quantity);

        Product product = findProductById(productId);
        product.setQuantity(product.getQuantity() + quantity);

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toDto(updatedProduct);
    }


    @Override
    public ProductDto updatePrice(Long productId, Double price) {
        logger.info("Updating price for product id {}: {}", productId, price);
        Product product = findProductById(productId);
        product.setPriceUnit(price);
        return ProductMapper.toDto(productRepository.save(product));
    }


    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
        } else {
            throw new IllegalArgumentException("Product not found with id: " + productId);
        }
    }


    public Product findProductById(Long productId) {
        logger.info("Finding product by id: {}", productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }
}
