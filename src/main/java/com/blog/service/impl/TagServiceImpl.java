package com.blog.service.impl;

import com.blog.Exception.NotFoundException;
import com.blog.dao.TagRepository;
import com.blog.po.Tag;
import com.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    private  List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids)&&ids!=null){
            String[] idarry = ids.split(",");
            for (int i = 0; i < idarry.length; i++) {
                list.add(new Long(idarry[i]));
            }
        }
        return list;
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAll(convertToList(ids));
    }

    @Transactional
    @Override
    public Tag updaTag(Long id, Tag tag) {
        Tag  t = tagRepository.findOne(id);
        if (t==null){
            throw new NotFoundException("id 不存在该类型");
        }
        BeanUtils.copyProperties(tag,t);

        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }
}
