package cn.sibat.ebase.server.dao;

import cn.sibat.ebase.server.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
