package com.geekbrains.backend.test.miniMarket;

import com.geekbrains.backend.test.miniMarket.model.Category;
import com.geekbrains.backend.test.miniMarket.model.Product;

import java.io.IOException;

class Test {
        public static void main(String[] args) throws IOException {
            miniMarketService service = new miniMarketService();
            var product = new Product();
            product.setTitle("Tomato");
            product.setPrice(50);
            product.setCategoryTitle("Food");
            var createdProduct = service.createProduct(product);
            Product receivedProduct = service.getProduct(createdProduct.getId());
            System.out.println(receivedProduct);
            receivedProduct.setPrice(80);
            service.updateProduct(receivedProduct);
            Product receivedProduct2 = service.getProduct(createdProduct.getId());
            System.out.println(receivedProduct2);
            Category category = service.getCategory(1);
            System.out.println(category);
            service.deleteProduct(receivedProduct2); // метод delete не работает
        }
}

