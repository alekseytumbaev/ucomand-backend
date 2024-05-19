package com.example.ucomandbackend.project;

import com.example.ucomandbackend.project.dto.ProjectDto;
import com.example.ucomandbackend.util.PageableMapper;
import com.example.ucomandbackend.vacancy.VacancyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/projects")
@RestController
@Validated
@RequiredArgsConstructor
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class ProjectController {

    private final ProjectService projectService;

    @Operation(description = "Текущий пользователь авоматически становится участником команды проекта")
    @PostMapping
    public ProjectDto addProject(@RequestBody @Validated ProjectDto projectDto) {
        return projectService.addProject(projectDto);
    }

    @GetMapping("/{projectId}")
    public ProjectDto getProject(@PathVariable Long projectId) {
        return projectService.getProject(projectId);
    }

    @GetMapping
    public Collection<ProjectDto> getAllProjects(@RequestParam(defaultValue = "0")
                                                 @Parameter(description = "min: 0")
                                                 @Validated @Min(0) Integer page,

                                                 @RequestParam(defaultValue = "10")
                                                 @Parameter(description = "min: 1")
                                                 @Validated @Min(1) Integer size) {
        return projectService.getAllProjects(PageableMapper.toPageableDto(page, size));
    }

    @GetMapping("/ofCurrentUser")
    public Collection<ProjectDto> getAllProjectsOfCurrentUser() {
        return projectService.getAllProjectsOfCurrentUser();
    }
}
