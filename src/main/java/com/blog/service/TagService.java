package com.blog.service;

import com.blog.po.Tag;
import com.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 类型业务层
 */
public interface TagService {

    /**
     * 新增
     * @param tag
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * 获取一个
     * @param id
     * @return
     */
    Tag getTagById(Long id);

    /**
     * 通过名称获取一个
     * @param name
     * @return
     */
    Tag getTagByName(String name);


    /**
     * 分页查找
     * @param pageable
     * @return
     */
    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);


    List<Tag> listTag(String ids);



    /**
     * 更新
     * @param id
     * @param tag
     * @return
     */
    Tag updaTag(Long id, Tag tag);

    /**
     * 删除
     * @param id
     */
    void deleteTag(Long id);


}
