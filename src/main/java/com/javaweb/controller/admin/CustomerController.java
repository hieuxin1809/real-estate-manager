package com.javaweb.controller.admin;

import com.javaweb.constant.SystemConstant;
import com.javaweb.converter.CustomerConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.enums.District;
import com.javaweb.enums.Status;
import com.javaweb.enums.TransactionType;
import com.javaweb.enums.TypeCode;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.CustomerService;
import com.javaweb.service.TransactionService;
import com.javaweb.service.impl.UserService;
import com.javaweb.utils.DisplayTagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController(value="customerControllerOfAdmin")
public class CustomerController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerConverter customerConverter;

    @RequestMapping(value = "/admin/customer-list", method = RequestMethod.GET)
    public ModelAndView getCustomer(@ModelAttribute("modelSearch") CustomerSearchRequest params , HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/customer/list");
        if(SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)){
            Long staffId = SecurityUtils.getPrincipal().getId();
            params.setStaffId(staffId);
        }
        CustomerSearchResponse response = new CustomerSearchResponse();
        List<CustomerSearchResponse> results = customerService.findAll(params);
        mav.addObject("staffs" , userService.getListStaff());
        mav.addObject("status", Status.statusType());
        mav.addObject("customerList", results);
        return mav;
    }
    @GetMapping("/admin/customer-edit")
    public ModelAndView addCustomer(@ModelAttribute("customer") CustomerDTO dto) {
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        mav.addObject("StatusList" , Status.statusType());
        return mav;
    }
    @GetMapping("/admin/customer-edit-{id}")
    public ModelAndView editCustomer(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        if(SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)){
            Long staffId = SecurityUtils.getPrincipal().getId();
            if(!customerService.isStaffOfCustomer(staffId,id)){
                mav.setViewName("error/404");
                return mav;
            }
        }
        mav.addObject("StatusList" , Status.statusType());
        mav.addObject("transactions" , TransactionType.transactionTypes());
        CustomerEntity customerEntity = customerService.findByIdAndIsActive(id,1);
        List<TransactionDTO> DDXTransaction = transactionService.findAllByCodeAndCustomer("DDX",customerEntity);
        List<TransactionDTO> CSKHTransaction = transactionService.findAllByCodeAndCustomer("CSKH",customerEntity);
        mav.addObject("customer", customerConverter.toCustomerDTO(customerEntity));
        mav.addObject("DDX", DDXTransaction);
        mav.addObject("CSKH", CSKHTransaction);
        return mav;
    }
}
