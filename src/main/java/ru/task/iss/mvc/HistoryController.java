package ru.task.iss.mvc;
/*
 * Date: 4/25/21
 * Time: 5:11 PM
 * */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.task.iss.entity.History;
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

}
