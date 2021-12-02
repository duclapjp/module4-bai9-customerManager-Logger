package com.codegym.cms.controller;

import com.codegym.cms.model.Province;
import com.codegym.cms.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/province")
public class ProvinceController {
    @Autowired
    private IProvinceService provinceService;
    @GetMapping("/view")
    public ModelAndView view(){
        Iterable<Province> provinces = provinceService.findAll();
        ModelAndView modelAndView = new ModelAndView("/province/list");
        modelAndView.addObject("provinces",provinces);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province",new Province());
        return modelAndView;
    }
    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Province province){
        ModelAndView modelAndView = new ModelAndView("/province/create");
        provinceService.save(province);
        modelAndView.addObject("message","Tạo mới thành công!");
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/province/edit");
        Optional<Province> province = provinceService.findById(id);
        modelAndView.addObject("province",province.get());
        return modelAndView;
    }
    @PostMapping("/update")
    public  ModelAndView update(@ModelAttribute Province province){
        ModelAndView modelAndView = new ModelAndView("/province/edit");
        modelAndView.addObject("message","chinh sua thanh cong");
        provinceService.save(province);
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/province/delete");
       Optional<Province> province = provinceService.findById(id);
       modelAndView.addObject("province",province.get());
       return modelAndView;
    }
    @PostMapping("/remove")
    public  ModelAndView remove(@RequestParam Long id){
        ModelAndView modelAndView = new ModelAndView("/province/delete");
        provinceService.remove(id);
        modelAndView.addObject("message","xoa thanh cong");
        return modelAndView;
    }
}
