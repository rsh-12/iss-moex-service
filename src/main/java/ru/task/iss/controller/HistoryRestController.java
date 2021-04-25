package ru.task.iss.controller;
/*
 * Date: 4/24/21
 * Time: 9:10 AM
 * */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "Histories")
@RestController
@RequestMapping("/api/histories")
public class HistoryRestController {

    private final HistoryService historyService;
    private final HistoryModelAssembler assembler;

    public HistoryRestController(HistoryService historyService, HistoryModelAssembler assembler) {
        this.historyService = historyService;
        this.assembler = assembler;
    }

    /* Import a History data from XML */
    @ApiOperation(value = "Import XML data")
    @PostMapping(value = "/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) throws IOException {
        historyService.importXmlData(file);
        return ResponseEntity.ok("Successfully processed");
    }

    /* Create a History */
    @ApiOperation(value = "Create a new History object")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createHistory(@Valid @RequestBody HistoryDto historyDto) {
        historyService.create(historyDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /* Get a list of histories */
    @ApiOperation(value = "Get a list of History objects")
    @GetMapping
    public CollectionModel<EntityModel<History>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "secId") String sort,
            @RequestParam(value = "date", required = false) LocalDate tradeDate
    ) {

        List<EntityModel<History>> histories = historyService
                .findAllHistories(pageNo, pageSize, sort, tradeDate).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(histories,
                linkTo(methodOn(HistoryRestController.class)
                        .findAll(pageNo, pageSize, sort, tradeDate)).withSelfRel());
    }

    /* Get the history by id */
    @ApiOperation(value = "Get the History object by ID")
    @GetMapping("/{id}")
    public EntityModel<History> findOne(@PathVariable("id") Long id) {
        History history = historyService.findById(id);
        return assembler.toModel(history);
    }

    /* Delete the history by id */
    @ApiOperation(value = "Delete the History by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Long id) {
        historyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Update the history by id  */
    @ApiOperation("Update the History object by ID")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Long id,
                                        @Valid @RequestBody HistoryDto historyDto) {
        historyService.update(id, historyDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
