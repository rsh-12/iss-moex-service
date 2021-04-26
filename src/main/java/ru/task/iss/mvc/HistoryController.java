package ru.task.iss.mvc;
/*
 * Date: 4/25/21
 * Time: 5:11 PM
 * */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.task.iss.entity.History;
import ru.task.iss.entity.Security;
import ru.task.iss.service.HistoryService;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public String getHistories(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
                               @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "sort", required = false, defaultValue = "secId") String sort,
                               Model model) {

        List<History> histories = historyService.findAllHistories(pageNo, pageSize, sort, null);

        model.addAttribute("page", pageNo);
        model.addAttribute("size", pageSize);
        model.addAttribute("sort", sort);
        model.addAttribute("histories", histories);
        model.addAttribute("reverseOrder", sort.contains("asc") ? "" : ",asc");

        return "history/history-table";
    }

    @PostMapping("/delete")
    public String deleteHistory(@RequestParam("id") Long id) {
        historyService.deleteById(id);
        return "redirect:/history";
    }

    @GetMapping("/{id}")
    public String getHistory(@PathVariable("id") Long id,
                             Model model) {
        History history = historyService.findById(id);
        model.addAttribute("history", history);
        return "history/history-form";
    }

    @GetMapping("/new")
    public String newHistory(Model model) {
        model.addAttribute("history", new History());
        return "security/security-form";
    }

}
