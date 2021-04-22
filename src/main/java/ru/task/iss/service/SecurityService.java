package ru.task.iss.service;

import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.entity.Security;

import java.io.IOException;

public interface SecurityService {

    void importXmlData(MultipartFile file) throws IOException;

    void create(Security security);

    // read
    // update
    // delete
}
