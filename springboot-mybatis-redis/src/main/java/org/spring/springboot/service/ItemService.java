package org.spring.springboot.service;

import org.spring.springboot.common.Result;
import org.spring.springboot.domain.Item;
import org.spring.springboot.domain.Person;
import org.spring.springboot.utils.Pager;

import java.util.Map;

public interface ItemService {

    /**
     * 新增
     */
    public Object insert(Item item);

    /**
     * 删除
     */
    public Result delete(int id);

    /**
     * 更新
     */
    public Result update(Item item);

    /**
     * 根据主键 id 查询
     */
    public Item load(int id);
        /**
     * 分页查询
     */
    public Pager<Item> findByItemPager(int page, int size);

}