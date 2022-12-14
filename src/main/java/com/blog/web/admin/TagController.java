package com.blog.web.admin;

import com.blog.po.Tag;
import com.blog.po.Type;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 跳转到input界面
     *
     * @return
     */
//    @GetMapping("/types/input")
//    public String input(){
//        return "admin/types-input";
//    }
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {

        model.addAttribute("tag", tagService.getTagById(id));
        return "admin/tags-input";
    }


    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

    /**
     * 保存 post请求
     *
     * @param tag
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result,
                       RedirectAttributes attributes) {

        Tag tag1 = tagService.getTagByName(tag.getName());

        if (tag1 != null) {
            attributes.addFlashAttribute("message", "标签: " + tag.getName() + " 已存在");
//            result.rejectValue("name","nameError","");
            return "redirect:/admin/tags";
        }
        if (result.hasErrors()) {
            return "redirect:/admin/tags";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            //如果保存失败
            attributes.addFlashAttribute("message", "添加标签: "+tag.getName()+" 失败");
        } else {
            attributes.addFlashAttribute("message", "添加标签: "+tag.getName()+" 成功");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes) {

        Tag tag1 = tagService.getTagByName(tag.getName());

        if (tag1 != null) {
            attributes.addFlashAttribute("message", "标签 " + tag.getName() + " 已存在");
//            result.rejectValue("name","nameError","");
            return "redirect:/admin/tags";
        }
        if (result.hasErrors()) {
            return "redirect:/admin/tags";
        }

        Tag t = tagService.updaTag(id, tag);

        if (t == null) {
            //如果保存失败
            attributes.addFlashAttribute("message", "修改标签: "+tag.getName()+" 失败");
        } else {
            attributes.addFlashAttribute("message", "修改标签: "+tag.getName()+" 成功");
        }
        return "redirect:/admin/tags";
    }
}
