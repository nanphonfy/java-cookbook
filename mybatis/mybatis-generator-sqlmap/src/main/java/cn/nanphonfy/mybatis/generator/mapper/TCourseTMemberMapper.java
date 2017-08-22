package main.java.cn.nanphonfy.mybatis.generator.mapper;

import java.util.List;
import main.java.cn.nanphonfy.mybatis.generator.entity.TCourseTMember;
import main.java.cn.nanphonfy.mybatis.generator.entity.TCourseTMemberExample;
import org.apache.ibatis.annotations.Param;

public interface TCourseTMemberMapper {
    long countByExample(TCourseTMemberExample example);

    int deleteByExample(TCourseTMemberExample example);

    int insert(TCourseTMember record);

    int insertSelective(TCourseTMember record);

    List<TCourseTMember> selectByExample(TCourseTMemberExample example);

    int updateByExampleSelective(@Param("record") TCourseTMember record, @Param("example") TCourseTMemberExample example);

    int updateByExample(@Param("record") TCourseTMember record, @Param("example") TCourseTMemberExample example);
}