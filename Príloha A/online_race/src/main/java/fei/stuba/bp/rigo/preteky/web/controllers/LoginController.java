package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.login.Role;
import fei.stuba.bp.rigo.preteky.models.login.User;
import fei.stuba.bp.rigo.preteky.repository.ClubRepository;
import fei.stuba.bp.rigo.preteky.repository.RolesRepository;
import fei.stuba.bp.rigo.preteky.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ClubRepository clubRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public LoginController(RolesRepository rolesRepository, UsersRepository usersRepository, ClubRepository clubRepository) {
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
        this.clubRepository = clubRepository;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "login";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
    @GetMapping(value = "/welcome")
    public String welcome(){
        return "welcomePage";
    }
    @GetMapping(value = "/register")
    public String register(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("clubs",clubRepository.findAll());
        return "register";
    }
    @PostMapping(value = "/register")
    public String registerUser(@Valid User user, BindingResult result, Model model){
        System.out.println(user);
        if (result.hasErrors()) {
            return "register";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        usersRepository.save(user);
        Role role = rolesRepository.findByName("REGISTERED");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        usersRepository.save(user);
        return "redirect:/login";
    }

}
