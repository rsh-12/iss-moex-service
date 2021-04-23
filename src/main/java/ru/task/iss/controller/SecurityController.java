package ru.task.iss.controller;
/*
 * Date: 4/20/21
 * Time: 7:56 PM
 * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.controller.assembler.SecurityModelAssembler;
import ru.task.iss.entity.Security;
import ru.task.iss.service.SecurityService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/securities")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    private final SecurityService securityService;
    private final SecurityModelAssembler assembler;

    @Autowired
    public SecurityController(SecurityService securityService, SecurityModelAssembler assembler) {
        this.securityService = securityService;
        this.assembler = assembler;
    }

    @PostMapping(value = "/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("Starting parsing data");
        securityService.importXmlData(file);
        return ResponseEntity.ok("Successfully processed");
    }

    @PostMapping
    public ResponseEntity<String> createSecurity(@Valid @RequestBody Security security) {
        securityService.create(security);
        return new ResponseEntity<>("Created a new security", HttpStatus.CREATED);
    }

    @GetMapping
    public CollectionModel<EntityModel<Security>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "secId") String sort,
            @RequestParam(value = "emitent_title", required = false) String emitentTitle
    ) {

        List<EntityModel<Security>> securities = securityService
                .findAllSecurities(pageNo, pageSize, sort, emitentTitle).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(securities,
                linkTo(methodOn(SecurityController.class)
                        .findAll(pageNo, pageSize, sort, emitentTitle)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Security> findOne(@PathVariable("id") Integer id) {
        Security security = securityService.findOneById(id);
        return assembler.toModel(security);
    }

}
