package cn.sibat.boot.server.service.impl;

import cn.sibat.boot.server.dao.ProductDao;
import cn.sibat.boot.server.dao.ProductRateDao;
import cn.sibat.boot.server.entity.Product;
import cn.sibat.boot.server.entity.ProductEarningRate;
import cn.sibat.boot.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/14
 */
@Service
public class ProductServiceImpl extends AbstractService implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductRateDao productRateDao;

    @Override
    public Product getById(Long id) {
        return productDao.getOne(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(Product product) {
        // 1.修改利率操作，先删除，再添加
        List<ProductEarningRate> pers = productRateDao.findByProductId((int) product.getProId());// 已经有的利率
        // 删除
        if (pers != null && pers.size() > 0) {
            productRateDao.deleteByProductId((int) product.getProId());
        }
        // 添加
        productRateDao.save(product.getProEarningRate());
        // 2.修改产品信息
        productDao.save(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProductByProId(Long proId) {
        //同时删除该产品的利率记录
        productRateDao.deleteByProductId(proId.intValue());
        productDao.delete(proId);
    }

    @Override
    public void saveProduct(Product product) {
        //添加商品
        productDao.save(product);
        //添加商品利率信息
        productRateDao.save(product.getProEarningRate());
    }
}
