package com.blog.web.admin;

import com.blog.po.Type;
import com.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeservice;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page", typeservice.listType(pageable));
        return "admin/types";
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
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {

        model.addAttribute("type", typeservice.getTypeById(id));
        return "admin/types-input";
    }


    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeservice.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

    /**
     * 保存 post请求
     *
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result,
                       RedirectAttributes attributes) {

        Type type1 = typeservice.getTypeByName(type.getName());

        if (type1 != null) {
            attributes.addFlashAttribute("message", "分类: " + type.getName() + " 已存在");
//            result.rejectValue("name","nameError","");
            return "redirect:/admin/types";
        }
        if (result.hasErrors()) {
            return "redirect:/admin/types";
        }
        Type t = typeservice.saveType(type);
        if (t == null) {
            //如果保存失败
            attributes.addFlashAttribute("message", "添加分类: "+type.getName()+" 失败");
        } else {
            attributes.addFlashAttribute("message", "添加分类: "+type.getName()+" 成功");
        }
        return "redirect:/admin/types";
    }


    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes) {

        Type type1 = typeservice.getTypeByName(type.getName());

        if (type1 != null) {
            attributes.addFlashAttribute("message", "分类 " + type.getName() + " 已存在");
//            result.rejectValue("name","nameError","");
            return "redirect:/admin/types";
        }
        if (result.hasErrors()) {
            return "redirect:/admin/types";
        }

        Type t = typeservice.updaType(id, type);

        if (t == null) {
            //如果保存失败
            attributes.addFlashAttribute("message", "修改分类: "+type.getName()+" 失败");
        } else {
            attributes.addFlashAttribute("message", "修改分类: "+type.getName()+" 成功");
        }
        return "redirect:/admin/types";
    }
}
