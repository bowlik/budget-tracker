package fei.upce.cz.semestralniprojekt;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto()); // Změněno z "user" na "userRegistrationDto"
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto userDto, 
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        
        try {
            userService.registerUser(userDto);
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}