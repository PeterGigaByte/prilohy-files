package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.login.Role;
import fei.stuba.bp.rigo.preteky.models.login.User;
import fei.stuba.bp.rigo.preteky.models.roles.Roles;
import fei.stuba.bp.rigo.preteky.repository.ClubRepository;
import fei.stuba.bp.rigo.preteky.repository.RolesRepository;
import fei.stuba.bp.rigo.preteky.repository.UsersRepository;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/roles")
public class RoleController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping(value = "")
    public String roles(Model model){
        model.addAttribute("users",usersRepository.findAll());
        model.addAttribute("clubs",clubRepository.findAll());
        model.addAttribute("roles",rolesRepository.findAll());
        return "roles";
    }
    @GetMapping(value = "/edit/{id}")
    public String roles(@PathVariable int id,Model model){
        User user = usersRepository.findByUserId(id);
        Roles roles = new Roles();
        for (Role role:user.getRoles()) {
            if(role.getName().equals("SUPERVISOR")){
                roles.setSupervisor(true);
            }if(role.getName().equals("ADMIN")){
                roles.setAdmin(true);
            }if(role.getName().equals("REGISTERED")){
                roles.setRegistered(true);
            }
        }
        model.addAttribute("user",user);
        model.addAttribute("clubs",clubRepository.findAll());
        model.addAttribute("roles",rolesRepository.findAll());
        model.addAttribute("roleCheck",roles);
        return "roleEdit";
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "roles";
    }
    @PostMapping(value = "/edit/{id}")
    public String roles(@PathVariable int id, @ModelAttribute User user,@ModelAttribute Roles roles){
        Set<Role> rolesSet = new HashSet<>();

        if(roles.getAdmin()){
            Role role = rolesRepository.findByName("ADMIN");
            if(role == null){
                role = rolesRepository.save(new Role("ADMIN"));
            }
            rolesSet.add(role);
        }
        if(roles.getSupervisor()){
            Role role = rolesRepository.findByName("SUPERVISOR");
            if(role == null){
                role = rolesRepository.save(new Role("SUPERVISOR"));
            }
            rolesSet.add(role);
        }
        if(roles.getRegistered()){
            Role role = rolesRepository.findByName("REGISTERED");
            if(role == null){
                role = rolesRepository.save(new Role("REGISTERED"));
            }
            rolesSet.add(role);
        }
        user.setRoles(rolesSet);
        usersRepository.save(user);
        return "redirect:/roles/edit/"+id;
    }
    @PostMapping(value = "")
    public String roles(){
        System.out.println("test");
        return "redirect:/roles";
    }
}
