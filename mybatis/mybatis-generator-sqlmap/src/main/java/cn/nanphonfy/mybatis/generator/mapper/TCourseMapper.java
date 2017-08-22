package main.java.cn.nanphonfy.mybatis.generator.mapper;

import java.util.List;
import main.java.cn.nanphonfy.mybatis.generator.entity.TCourse;
import main.java.cn.nanphonfy.mybatis.generator.entity.TCourseExample;
import org.apache.ibatis.annotations.Param;

public interface TCourseMapper {
    long countByExample(TCourseExample example);

    int deleteByExample(TCourseExample example);

    int deleteByPrimaryKey(Integer courseid);

    int insert(TCourse record);

    int insertSelective(TCourse record);

    List<TCourse> selectByExample(TCourseExample example);

    TCourse selectByPrimaryKey(Integer courseid);

    int updateByExampleSelective(@Param("record") TCourse record, @Param("example") TCourseExample example);

    int updateByExample(@Param("record") TCourse record, @Param("example") TCourseExample example);

    int updateByPrimaryKeySelective(TCourse record);

    int updateByPrimaryKey(TCourse record);
}