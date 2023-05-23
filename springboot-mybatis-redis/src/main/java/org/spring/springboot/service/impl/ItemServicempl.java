package org.spring.springboot.service.impl;

import org.spring.springboot.common.Result;
import org.spring.springboot.dao.ItemMapper;
import org.spring.springboot.domain.Item;
import org.spring.springboot.service.ItemService;
import org.spring.springboot.utils.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description item_and_d
 * @author zhengkai.blog.csdn.net
 * @date 2023-05-17
 */
@Service
public class ItemServicempl implements ItemService{

    @Resource
    private ItemMapper itemMapper;


    @Override
    public Object insert(Item item) {

        // valid
        if (item == null) {
            return new Result();
        }

        itemMapper.insert(item);
        return new Result();
    }


    @Override
    public Result delete(int id) {
        int ret = itemMapper.delete(id);
        return new Result();
    }


    @Override
    public Result update(Item item) {
        int ret = itemMapper.update(item);
        return new Result();
    }


    @Override
    public Item load(int id) {
        return itemMapper.load(id);
    }
    @Override
    public Pager<Item> findByItemPager(int page, int size){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", (page-1)*size);
        params.put("size", size);
        List<Item> list = itemMapper.pageList(params);
        Pager<Item> pager = new Pager<Item>();
        pager.setData(list);
        pager.setTotal(itemMapper.pageListCount());
        pager.setPage(page);
        pager.setSize(size);
        System.out.println("!!!!!!!!!!!");
        return pager;
    }

//    @Override
//    public Map<String,Object> pageList(int offset, int pagesize) {
//
//        List<Item> pageList = itemMapper.pageList(offset, pagesize);
//        int totalCount = itemMapper.pageListCount(offset, pagesize);
//
//        // result
//        Map<String, Object> result = new HashMap<String, Object>();
//        result.put("pageList", pageList);
//        result.put("totalCount", totalCount);
//
//        return result;
//    }

}