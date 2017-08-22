package main.java.cn.nanphonfy.mybatis.generator.mapper;

import java.util.List;
import main.java.cn.nanphonfy.mybatis.generator.entity.TMember;
import main.java.cn.nanphonfy.mybatis.generator.entity.TMemberExample;
import main.java.cn.nanphonfy.mybatis.generator.entity.TMemberWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface TMemberMapper {
    long countByExample(TMemberExample example);

    int deleteByExample(TMemberExample example);

    int deleteByPrimaryKey(Integer memberid);

    int insert(TMemberWithBLOBs record);

    int insertSelective(TMemberWithBLOBs record);

    List<TMemberWithBLOBs> selectByExampleWithBLOBs(TMemberExample example);

    List<TMember> selectByExample(TMemberExample example);

    TMemberWithBLOBs selectByPrimaryKey(Integer memberid);

    int updateByExampleSelective(@Param("record") TMemberWithBLOBs record, @Param("example") TMemberExample example);

    int updateByExampleWithBLOBs(@Param("record") TMemberWithBLOBs record, @Param("example") TMemberExample example);

    int updateByExample(@Param("record") TMember record, @Param("example") TMemberExample example);

    int updateByPrimaryKeySelective(TMemberWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TMemberWithBLOBs record);

    int updateByPrimaryKey(TMember record);
}