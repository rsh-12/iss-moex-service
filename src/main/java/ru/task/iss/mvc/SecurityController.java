package ru.task.iss.mvc;
/*
 * Date: 4/25/21
 * Time: 5:11 PM
 * */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.entity.Security;
import ru.task.iss.service.SecurityService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public String getSecurities(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "sort", required = false, defaultValue = "secId") String sort,
                                Model model) {

        List<Security> securities = securityService.findAllSecurities(pageNo, pageSize, sort, null);

        model.addAttribute("page", pageNo);
        model.addAttribute("size", pageSize);
        model.addAttribute("sort", sort);
        model.addAttribute("securities", securities);
        model.addAttribute("reverseOrder", sort.contains("asc") ? "" : ",asc");

        return "security/security-table";
    }

    @PostMapping("/delete")
    public String deleteSecurity(@RequestParam("id") Integer id) {
        securityService.deleteById(id);
        return "redirect:/security";
    }

    @GetMapping("/{id}")
    public String getSecurity(@PathVariable("id") Integer id,
                              Model model) {
        Security security = securityService.findById(id);
        model.addAttribute("security", security);
        return "security/security-form";
    }

    @GetMapping("/new")
    public String newSecurity(Model model) {
        model.addAttribute("security", new Security());
        return "security/security-form";
    }

    @PostMapping("/save")
    public String saveSecurity(@Valid @ModelAttribute Security security,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "security/security-form";
        }

        securityService.save(security);
        return "redirect:/security";
    }
}
