package com.ikun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ikun.entity.Dict;
import com.ikun.mapper.DictMapper;
import com.ikun.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        List<Map<String, Object>> result = new ArrayList<>();

        //想要的结果：[{ id:'0331',name:'n3.3.n1',	isParent:true}...]
        List<Dict> dictList = dictMapper.findListByParentId(id);
        //遍历集合
        for (Dict dict : dictList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dict.getId());
            map.put("name", dict.getName());
            //调用isParentNode，查看此节点是否还有子节点
            boolean isParentNode = dictMapper.isParentNode(dict.getId());
            map.put("isParent", isParentNode);
            result.add(map);
        }

        return result;
    }

    //根据编码获取改节点下所有的子节点
    @Override
    public List<Dict> getListByDictCode(String dictCode) {
        //获取该节点
        Dict dict = dictMapper.getDictByDictCode(dictCode);
        if (dict == null) return null;
        //获取该节点下的子节点
        List<Dict> dictList = dictMapper.findListByParentId(dict.getId());
        return dictList;
    }

    //根据父id获取改节点下所有的子节点
    @Override
    public List<Dict> getListByParentId(Long parentId) {
        List<Dict> dictList = dictMapper.findListByParentId(parentId);
        return dictList;
    }

    @Override
    public String getNameById(Long id) {
        return dictMapper.getNameById(id);
    }
}
