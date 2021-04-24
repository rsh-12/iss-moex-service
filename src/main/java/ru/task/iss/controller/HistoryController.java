package ru.task.iss.controller;
/*
 * Date: 4/24/21
 * Time: 9:10 AM
 * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.controller.assembler.HistoryModelAssembler;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.Security;
import ru.task.iss.service.HistoryService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/histories")
public class HistoryController {

    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);

    private final HistoryService historyService;
    private final HistoryModelAssembler assembler;

    public HistoryController(HistoryService historyService, HistoryModelAssembler assembler) {
        this.historyService = historyService;
        this.assembler = assembler;
    }

    /* Import History data from XML */
    @PostMapping(value = "/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("Starting parsing data");
        historyService.importXmlData(file);
        return ResponseEntity.ok("Successfully processed");
    }

    /* Create a Security */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createHistory(@Valid @RequestBody HistoryDto historyDto) {
        historyService.create(historyDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Class<?> findOne(Long id) {
        return null;
    }

    public Class<?> findAll(Object o, Object o1, Object o2, Object o3) {
        return null;
    }
}
