package ru.Bodyaaaa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.Bodyaaaa.dto.MasterDTO;
import ru.Bodyaaaa.service.MasterService;

@Controller
@RequestMapping("/res")
public class LoginController {

    private final MasterService masterService;

    @Autowired
    public LoginController(MasterService masterService, PasswordEncoder passwordEncoder) {
        this.masterService = masterService;
    }


    @GetMapping("/register")
    public String registerPage() {
        return "registerUser";
    }


    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }

    @PostMapping("/register")
    public String register(@RequestBody MasterDTO masterDTO) {
        if (masterService.existsByUsername(masterDTO.getUsername())) {
            return "redirect:/res/register?error=User+already+exists";
        }
        masterService.save(masterDTO);
        return "redirect:/login";
    }


}
