package cn.sibat.boot.server.dao;

import cn.sibat.boot.server.entity.Creditor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/13
 */
public interface CreditorDao extends JpaRepository<Creditor, Long> {
}
