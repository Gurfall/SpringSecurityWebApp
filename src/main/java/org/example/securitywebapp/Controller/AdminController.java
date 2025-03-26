package org.example.securitywebapp.Controller;
//
//import org.example.securitywebapp.Model.User;
//import org.example.securitywebapp.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping()
//    public String getAllUsers(Model model) {
//        List<User> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }
//
//    @GetMapping("/remove/{id}")
//    public String removeUser(@PathVariable Integer id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/add")
//    public String addUserForm(Model model) {
//        model.addAttribute("user", new User());
//        return "user-form";
//    }
//
//    @PostMapping("/add")
//    public String addUser(@ModelAttribute User user) {
//        userService.createUser(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/update/{id}")
//    public String updateUserForm(@PathVariable Integer id, Model model) {
//        User user = userService.findUserById(id);
//        model.addAttribute("user", user);
//        return "user-form";
//    }
//
//    @PostMapping("/update")
//    public String updateUser(@ModelAttribute User user) {
//        userService.updateUser(user);
//        return "redirect:/admin";
//    }
//
//}
//

import org.example.securitywebapp.Model.User;
import org.example.securitywebapp.PasswordEncryption;
import org.example.securitywebapp.Service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private static String password;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}")
    public String user(Model model, @PathVariable Integer id) {
        model.addAttribute("user", userService.findUserById(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new-user";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user")
                                @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new-user";
        }
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Integer id, Model model) {
        User user = userService.findUserById(id);
        password = user.getPassword();
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute("user")
    @Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {return "editUser";}
        if (!user.getPassword().equals(password)) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/clear")
    public String clearUsers() {
        userService.clearUsers();
        return "redirect:/admin";
    }
}
