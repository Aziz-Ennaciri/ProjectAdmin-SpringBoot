package com.adminproject.admin.Web;

import com.adminproject.admin.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private IUserService userService;


    @GetMapping("/users")
    public boolean checkIfEmailExist(@RequestParam(name = "email",defaultValue = "")String email){
        return userService.loadUserByEmail(email) != null;
    }
}
