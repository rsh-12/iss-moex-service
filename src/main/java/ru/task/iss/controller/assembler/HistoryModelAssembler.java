package ru.task.iss.controller.assembler;
/*
 * Date: 4/22/21
 * Time: 1:02 PM
 * */

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.task.iss.controller.HistoryController;
import ru.task.iss.entity.History;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HistoryModelAssembler
        implements RepresentationModelAssembler<History, EntityModel<History>> {

    @Override
    public EntityModel<History> toModel(History entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(HistoryController.class)
                        .findOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(HistoryController.class)
                        .findAll(null, null, null, null)).withRel("histories"));
    }
}
