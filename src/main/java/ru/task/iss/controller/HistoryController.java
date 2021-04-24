package ru.task.iss.controller;
/*
 * Date: 4/24/21
 * Time: 9:10 AM
 * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.controller.assembler.HistoryModelAssembler;
import ru.task.iss.dto.HistoryDto;
import ru.task.iss.entity.History;
import ru.task.iss.service.HistoryService;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    /* Create a History */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createHistory(@Valid @RequestBody HistoryDto historyDto) {
        historyService.create(historyDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /* Get list of histories */
    @GetMapping
    public CollectionModel<EntityModel<History>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "secId") String sort,
            @RequestParam(value = "trade_date", required = false) LocalDate tradeDate
    ) {

        List<EntityModel<History>> histories = historyService
                .findAllHistories(pageNo, pageSize, sort, tradeDate).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(histories,
                linkTo(methodOn(HistoryController.class)
                        .findAll(pageNo, pageSize, sort, tradeDate)).withSelfRel());
    }

    /* Get the history by id */
    @GetMapping("/{id}")
    public EntityModel<History> findOne(@PathVariable("id") Long id) {
        History history = historyService.findById(id);
        return assembler.toModel(history);
    }

    /* Delete the history by id */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Long id) {
        historyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Update the history  */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Long id,
                                        @Valid @RequestBody HistoryDto historyDto) {
        historyService.update(id, historyDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
