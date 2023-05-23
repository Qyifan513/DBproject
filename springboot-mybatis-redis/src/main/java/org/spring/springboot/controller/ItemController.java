package org.spring.springboot.controller;

import org.spring.springboot.common.Result;
import org.spring.springboot.domain.Item;
import org.spring.springboot.service.ItemService;
import org.spring.springboot.utils.Pager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description item_and_d
 * @author zhengkai.blog.csdn.net
 * @date 2023-05-17
 */
@RestController
public class ItemController{

    @Resource
    private ItemService itemService;

    /**
     * 新增
     *
     * @date 2023/05/17
     **/
    @RequestMapping(value ="/admin/item/insert",method = RequestMethod.POST )
    public Object insert(Item item){
        return itemService.insert(item);
    }

    /**
     * 刪除
     *
     * @date 2023/05/17
     **/
    @RequestMapping(value ="/admin/item/delete",method = RequestMethod.DELETE)
    public Result delete(int id){
        return itemService.delete(id);
    }

    /**
     * 更新
     *
     * @date 2023/05/17
     **/
    @RequestMapping(value ="/admin/item/update",method = RequestMethod.POST)
    public Result update(Item item){
        return itemService.update(item);
    }

    /**
     * 查询 根据主键 id 查询
     *
     * @date 2023/05/17
     **/
    @RequestMapping(value ="/admin/item/load", method = RequestMethod.GET)
    public Item load(int id){
        return itemService.load(id);
    }

    /**
     * 查询 分页查询
     * @date 2023/05/17
     **/
    @RequestMapping(value ="/admin/item/show", method = RequestMethod.GET)
    public Pager<Item> searchList(int page, int size){
        return itemService.findByItemPager(page,size);
    }
}