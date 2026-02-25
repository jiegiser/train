package org.jiegiser.train.business.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jiegiser.train.business.domain.SkToken;
import org.jiegiser.train.business.domain.SkTokenExample;

public interface SkTokenMapper {
    long countByExample(SkTokenExample example);

    int deleteByExample(SkTokenExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SkToken row);

    int insertSelective(SkToken row);

    List<SkToken> selectByExample(SkTokenExample example);

    SkToken selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") SkToken row, @Param("example") SkTokenExample example);

    int updateByExample(@Param("row") SkToken row, @Param("example") SkTokenExample example);

    int updateByPrimaryKeySelective(SkToken row);

    int updateByPrimaryKey(SkToken row);
}