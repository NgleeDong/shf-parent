package com.ikun.service;

import com.ikun.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {

    List<Map<String, Object>> findZnodes(Long id);

    //根据编码获取改节点下所有的子节点
    List<Dict> getListByDictCode(String dictCode);

    //根据父id获取改节点下所有的子节点,如用来根据东城的id获取东城的所有版块
    List<Dict> getListByParentId(Long parentId);

    String getNameById(Long id);
}
