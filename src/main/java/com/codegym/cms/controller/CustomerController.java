package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.service.ICustomerService;
import com.codegym.cms.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProvinceService provinceService;

    @ModelAttribute("provinces")
    public  Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/view")
    public ModelAndView listCustomers(@RequestParam Optional<String> search,@PageableDefault (value = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("customer/list");
        Page<Customer> customers;
        if (search.isPresent()){
            customers = customerService.findAllByFirstNameContaining(search.get(),pageable);
            modelAndView.addObject("s", search.get());
        }else {
            customers = customerService.findAll(pageable);
        }
        modelAndView.addObject("customers",customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customer/view";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/delete");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        customerService.remove(id);
        return "redirect:/customer/view";
    }
}
