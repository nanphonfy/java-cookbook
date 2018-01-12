package cn.sibat.ebase.server.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author nanphonfy(南风zsr)
 * @date 2018/1/12
 */
@Entity
public class UserStation implements Serializable{
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id; // 用户的唯一标识

    @Column(nullable = false) // 映射为字段，值不能为空
    private Long userId;

    @Column(nullable = false) // 映射为字段，值不能为空
    private String stationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    protected UserStation() { // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
    }
    public UserStation(Long userId, String stationId) {
        this.userId = userId;
        this.stationId = stationId;
    }
}
