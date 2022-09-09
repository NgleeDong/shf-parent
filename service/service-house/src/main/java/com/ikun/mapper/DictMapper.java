package com.ikun.mapper;

import com.ikun.entity.Dict;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictMapper {
//    对数据字典只有查询的操作，没有增删改

    //根据父id查询该节点下所有的子节点
    List<Dict> findListByParentId(Long parentId);

    //判断某个节点是否是父节点:通过它的id查询其子节点的数量是否 > 0
    boolean isParentNode(Long parentId);

    //根据编码获取Dict对象，如传的dictCode是“beijing”
    Dict getDictByDictCode(String dictCode);

    //根据id获取name，这个方法用来小区管理功能获取区域和版块
    String getNameById(Long id);

}
