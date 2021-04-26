package ru.task.iss.mvc;
/*
 * Date: 4/25/21
 * Time: 11:02 AM
 * */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.task.iss.dto.SecurityHistoryDto;
import ru.task.iss.service.SecurityService;

import java.util.List;

@Controller
public class MainController {

    private final SecurityService securityService;

    public MainController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/view")
    public String getView(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
                          @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "sort", required = false, defaultValue = "secId") String sort,
                          Model model) {

        List<SecurityHistoryDto> fields = securityService.findViewFields(pageNo, pageSize, sort);

        model.addAttribute("page", pageNo);
        model.addAttribute("size", pageSize);
        model.addAttribute("sort", sort);
        model.addAttribute("fields", fields);
        model.addAttribute("reverseOrder", sort.contains("asc") ? "" : ",asc");

        return "view";
    }
}
