package com.geekbrains.backend.test.miniMarket;

import com.geekbrains.backend.test.miniMarket.model.Category;
import com.geekbrains.backend.test.miniMarket.model.Product;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class miniMarketService {

    private final MiniMarketApi api;

    public miniMarketService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://minimarket1.herokuapp.com/market/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            api = retrofit.create(MiniMarketApi.class);
    }

    public Product getProduct (Long id) throws IOException {
        Response<Product> response = api.getProduct(id).execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        else {
            throw new RuntimeException("product not found");
        }
    }

    public Product createProduct(Product product) throws IOException {
        Response<Product> response = api.createProduct(product).execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        else {
            throw new RuntimeException("unable to create product");
        }
    }
    public Product updateProduct (Product product) throws IOException {
        Response<Product> response = api.updateProduct(product).execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        else {
            throw new RuntimeException("product not found");
        }
    }
    public void deleteProduct (Product product) throws IOException {
        Response<Object> response = api.deleteProduct(product.getId()).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("product not found");
        }
    }

    public Category getCategory (Integer id) throws IOException {
        Response<Category> response = api.getCategory(id).execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        else {
            throw new RuntimeException("category not found");
        }
    }

}
