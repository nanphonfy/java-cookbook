package cn.sibat.boot.server.service;

import cn.sibat.boot.server.entity.Product;

import java.util.List;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/14
 */
public interface ProductService {
    Product getById(Long aLong);

    List<Product> getAllProducts();

    void updateProduct(Product product);

    void deleteProductByProId(Long proId);

    void saveProduct(Product product);
}
