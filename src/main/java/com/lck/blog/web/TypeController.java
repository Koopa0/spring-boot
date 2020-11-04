package com.lck.blog.web;

import com.lck.blog.po.Type;
import com.lck.blog.service.TypeService;
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
@RequestMapping("/login")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    //根據前端的頁面構造好的參數會封裝到pageable裡面
    //@PageableDefault可以指定參數 分頁的大小 size = 10,根據id用倒敘的方式來排序sort = {"id"},direction = Sort.Direction.DESC
    //model是前端頁面的展示模型
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        //把查詢出來的東西放到前端展示頁面,page是查詢出來的頁面
        model.addAttribute("page",typeService.listType(pageable));
        return "types";
    }

    //返回一個新增的頁面
    @GetMapping("/types/input")
    //跳轉types頁面的時候往model裡面賦值一個type
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "types-input";
    }

    //利用id來查詢Type
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "types-input";
    }


    @PostMapping("/types")
    //@Valid代表要校驗type對象, BindingResult result接收校驗之後的結果
    public String post(@Valid Type type,BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        //不等於空的話代表重複
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重複的分類");
        }
        if (result.hasErrors()) {
            return "types-input";
        }
        Type t = typeService.saveType(type);
        //新增後對象
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/login/types";
    }


    //跟新增同理
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重複的分類");
        }
        if (result.hasErrors()) {
            return "types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/login/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/login/types";
    }


}
