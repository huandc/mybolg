package com.blog.service;

import com.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 类型业务层
 */
public interface TypeService {

    /**
     * 新增
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * 获取一个
     * @param id
     * @return
     */
    Type getTypeById(Long id);

    /**
     * 通过名称获取一个
     * @param name
     * @return
     */
    Type getTypeByName(String name);


    /**
     * 分页查找
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    /**
     * 更新
     * @param id
     * @param type
     * @return
     */
    Type updaType(Long id,Type type);

    /**
     * 删除
     * @param id
     */
    void deleteType(Long id);


}
