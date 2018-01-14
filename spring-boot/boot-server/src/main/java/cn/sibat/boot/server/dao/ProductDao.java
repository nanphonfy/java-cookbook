package cn.sibat.boot.server.dao;

import cn.sibat.boot.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/14
 */
public interface ProductDao extends JpaRepository<Product,Long>{
}
