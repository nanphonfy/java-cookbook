package cn.sibat.boot.server.dao;

import cn.sibat.boot.server.entity.ProductEarningRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/14
 */
public interface ProductRateDao extends JpaRepository<ProductEarningRate, Integer> {
    /**
     * 查看产品的利率信息
     * @param proId
     * @return
     */
    List<ProductEarningRate> findByProductId(int proId);

    /**
     * 方法命令必须遵循JPA规范，By后必须为类中的属性
     * 根据产品ID（非主键）进行删除
     * @param i 产品ID
     */
    void deleteByProductId(int i);
}
