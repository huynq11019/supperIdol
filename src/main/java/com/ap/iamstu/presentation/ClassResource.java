package com.ap.iamstu.presentation;

import com.ap.iamstu.application.dto.request.ClassCreateRequest;
import com.ap.iamstu.application.dto.request.ClassUpdateRequest;
import com.ap.iamstu.application.dto.request.FindByIds;
import com.ap.iamstu.domain.Class;
import com.ap.iamstu.infrastructure.support.query.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Class Resource (Quản lý các thônh tin của các lớp học)")
@RequestMapping("/api")
public interface ClassResource {

    // create
    @ApiOperation(value = "Create class")
    @PostMapping("/classes")
    @PreAuthorize("hasPermission(null, 'class:create')")
    Response<Class> createClass(@RequestBody @Valid ClassCreateRequest request);

    //    update
    @ApiOperation(value = "Update class")
    @PutMapping("/classes/{id}")
    @PreAuthorize("hasPermission(null, 'class:update')")
    Response<Class> updateClass(@PathVariable("id") String id, @RequestBody @Valid ClassUpdateRequest request);

    // find by id
    @ApiOperation(value = "Find class by id")
    @GetMapping("/classes/{id}")
    @PreAuthorize("hasPermission(null, 'class:view')")
    Response<Optional<Class>> findClassById(@PathVariable("id") String id);

    @ApiOperation(value = "Find class by ids")
    @PostMapping("/classes/find-by-ids")
    @PreAuthorize("hasPermission(null, 'class:view')")
    Response<List<Class>> findClassByIds(@Valid FindByIds id);

    // add Student

//    remove Student

//    add Teacher

//    remove Teacher

//    add Subject

//    remove Subject

//    add Room

//    remove Room

//    add Class

//    remove Class

//    add ClassSchedule

//    remove ClassSchedule

//    add ClassStudent

//    remove ClassStudent

//    add ClassTeacher

//    remove ClassTeacher

//    add ClassSubject

//    remove ClassSubject
}
