package com.example.ucomandbackend.project;

import com.example.ucomandbackend.error_handling.NotFoundException;
import com.example.ucomandbackend.project.dto.ProjectDto;
import com.example.ucomandbackend.user.UserService;
import com.example.ucomandbackend.util.AuthUtils;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepo;
    private final UserService userService;

    public Collection<ProjectDto> getAllProjectsOfCurrentUser() {
        var userId = AuthUtils.extractUserIdFromJwt();
        return projectRepo.findAllByUserId(userId).stream().map(ProjectMapper::toProjectDto).toList();
    }

    public ProjectDto addProject(ProjectDto projectDto) {
        var userId = AuthUtils.extractUserIdFromJwt();
        var user = userService.getUserById(userId);
        var project = ProjectMapper.toProject(projectDto, new HashSet<>(List.of(user)), new HashSet<>());
        return ProjectMapper.toProjectDto(projectRepo.save(project));
    }

    public ProjectDto getProject(Long projectId) {
        return ProjectMapper.toProjectDto(projectRepo.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Проект не найден")));
    }

    public Collection<ProjectDto> getAllProjects(PageableDto pageableDto) {
        return projectRepo.findAll(PageableMapper.toPageable(pageableDto))
                .stream().map(ProjectMapper::toProjectDto).toList();
    }
}
