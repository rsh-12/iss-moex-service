package ru.task.iss.controller;
/*
 * Date: 4/20/21
 * Time: 7:56 PM
 * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.entity.Security;
import ru.task.iss.service.SecurityService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/securities")
public class SecurityController {

    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
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
}
