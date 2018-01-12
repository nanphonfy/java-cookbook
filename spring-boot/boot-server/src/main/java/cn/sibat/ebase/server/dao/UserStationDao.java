package cn.sibat.ebase.server.dao;

import cn.sibat.ebase.server.entity.UserStation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/12
 */
public interface UserStationDao extends JpaRepository<UserStation, Long> {
}
