package ru.task.iss.service;

import org.springframework.web.multipart.MultipartFile;
import ru.task.iss.entity.Security;

import java.io.IOException;
import java.util.List;

public interface SecurityService {

    void importXmlData(MultipartFile file) throws IOException;

    void create(Security security);

    List<Security> findAllSecurities(Integer pageNo, Integer pageSize, String sort, String emitentId);

    Security findOneById(Integer id);

    // read
    // update
    // delete
}
