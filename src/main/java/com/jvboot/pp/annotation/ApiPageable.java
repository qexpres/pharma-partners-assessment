package com.jvboot.pp.annotation;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation on every method where you would like to use the Pageable and use @ApiIgnore in front of the
 * Pageable argument to prevent garbage output in the Swagger documentation.
 */
@Target(
    {ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE}
)
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams(
    {
        @ApiImplicitParam(
            name = "page",
            dataType = "int",
            paramType = "query",
            value = "Number of the page that you want to retrieve starting at 1."
        ),
        @ApiImplicitParam(
            name = "size",
            dataType = "int",
            paramType = "query",
            value = "Number of records per page."
        ),
        @ApiImplicitParam(
            name = "sort",
            allowMultiple = true,
            dataType = "string",
            paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. "
                + "Multiple sort criteria are supported."
        )
    }
)
public @interface ApiPageable {
}
