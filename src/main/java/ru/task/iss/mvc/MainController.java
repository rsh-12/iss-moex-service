package ru.task.iss.mvc;
/*
 * Date: 4/25/21
 * Time: 11:02 AM
 * */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/view")
    public String getView() {
        return "view";
    }
}
