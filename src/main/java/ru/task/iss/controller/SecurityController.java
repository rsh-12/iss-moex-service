package ru.task.iss.controller;
/*
 * Date: 4/20/21
 * Time: 7:56 PM
 * */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.controller.assembler.SecurityHistoryDtoModelAssembler;
import ru.task.iss.controller.assembler.SecurityModelAssembler;
import ru.task.iss.dto.SecurityDto;
import ru.task.iss.dto.SecurityHistoryDto;
import ru.task.iss.entity.Security;
import ru.task.iss.service.SecurityService;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = "Securities")
@RestController
@RequestMapping("/api/securities")
public class SecurityController {

    private final SecurityService securityService;
    private final SecurityModelAssembler assembler;
    private final SecurityHistoryDtoModelAssembler specificFieldsAssembler;

    @Autowired
    public SecurityController(SecurityService securityService, SecurityModelAssembler assembler,
                              SecurityHistoryDtoModelAssembler specificFieldsAssembler) {
        this.securityService = securityService;
        this.assembler = assembler;
        this.specificFieldsAssembler = specificFieldsAssembler;
    }

    /* Import securities data from XML */
    @ApiOperation(value = "Import XML data")
    @PostMapping(value = "/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) throws IOException {
        securityService.importXmlData(file);
        return ResponseEntity.ok("Successfully processed");
    }

    /* Create a Security */
    @ApiOperation(value = "Create a new Security object")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createSecurity(@Valid @RequestBody Security security) {
        securityService.create(security);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /* Get a list of securities */
    @ApiOperation(value = "Get a list of Security objects")
    @GetMapping
    public CollectionModel<EntityModel<Security>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "secId") String sort,
            @RequestParam(value = "title", required = false) String emitentTitle
    ) {

        List<EntityModel<Security>> securities = securityService
                .findAllSecurities(pageNo, pageSize, sort, emitentTitle).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(securities,
                linkTo(methodOn(SecurityController.class)
                        .findAll(pageNo, pageSize, sort, emitentTitle)).withSelfRel());
    }

    /* Get the security object by id */
    @ApiOperation(value = "Get the Security object by ID")
    @GetMapping("/{id}")
    public EntityModel<Security> findOne(@PathVariable("id") Integer id) {
        Security security = securityService.findById(id);
        return assembler.toModel(security);
    }

    /* Delete the security by id */
    @ApiOperation(value = "Delete the Security by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Integer id) {
        securityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Update the security */
    @ApiOperation("Update the Security object by ID")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @Valid @RequestBody SecurityDto securityDto) {
        securityService.update(id, securityDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /* Get a list of specific fields */
    @ApiOperation(value = "Get secid, regnumber, name, emitent_title, tradedate, numtrades, open, close")
    @GetMapping("/view")
    public CollectionModel<EntityModel<SecurityHistoryDto>> view(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "secId") String sort,
            @RequestParam(value = "title", required = false) String emitentTitle,
            @RequestParam(value = "date", required = false) LocalDate tradeDate
    ) {

        List<EntityModel<SecurityHistoryDto>> securities = securityService
                .findSpecificFields(pageNo, pageSize, sort, emitentTitle, tradeDate).stream()
                .map(specificFieldsAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(securities,
                linkTo(methodOn(SecurityController.class)
                        .view(pageNo, pageSize, sort, emitentTitle, tradeDate)).withSelfRel());
    }

}
