package ru.task.iss.controller.assembler;
/*
 * Date: 4/22/21
 * Time: 1:02 PM
 * */

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.task.iss.controller.SecurityController;
import ru.task.iss.entity.Security;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SecurityModelAssembler
        implements RepresentationModelAssembler<Security, EntityModel<Security>> {

    @Override
    public EntityModel<Security> toModel(Security entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(SecurityController.class)
                        .findAll(null, null, null, null)).withRel("securities"));
    }
}
