package ru.task.iss.controller.assembler;
/*
 * Date: 4/25/21
 * Time: 8:27 AM
 * */

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.task.iss.controller.SecurityController;
import ru.task.iss.dto.SecurityHistoryDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SecurityHistoryDtoModelAssembler
        implements RepresentationModelAssembler<SecurityHistoryDto, EntityModel<SecurityHistoryDto>> {

    @Override
    public EntityModel<SecurityHistoryDto> toModel(SecurityHistoryDto entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(SecurityController.class)
                        .view(null, null, null, null, null))
                        .withRel("view"));
    }
}
