package com.example.ucomandbackend.vacancy;

import com.example.ucomandbackend.error_handling.NotFoundException;
import com.example.ucomandbackend.project.ProjectRepository;
import com.example.ucomandbackend.tags.TagService;
import com.example.ucomandbackend.tags.dto.TagDto;
import com.example.ucomandbackend.user.UserService;
import com.example.ucomandbackend.util.AuthUtils;
import com.example.ucomandbackend.util.CollectionUtils;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepo;
    private final UserService userService;
    private final TagService tagService;
    private final ProjectRepository projectRepo;

    public Collection<VacancyDto> getAllVacanciesByTagIds(PageableDto pageableDto, List<Long> tagIds) {
        Page<Vacancy> vacancies;
        if (CollectionUtils.notEmpty(tagIds)) {
            vacancies = vacancyRepo.findAllByTagIdsIn(PageableMapper.toPageable(pageableDto), tagIds);
        } else {
            vacancies = vacancyRepo.findAll(PageableMapper.toPageable(pageableDto));
        }
        return vacancies.stream().map(
                VacancyMapper::toVacancyDto).toList();
    }

    public Collection<VacancyDto> getAllVacanciesOfCurrentUser() {
        long userId = AuthUtils.extractUserIdFromJwt();
        return vacancyRepo.findAllByOwner_Id(userId).stream().map(VacancyMapper::toVacancyDto).toList();
    }

    public VacancyDto getVacancyById(Long vacancyId) {
        return VacancyMapper.toVacancyDto(
                vacancyRepo.findById(vacancyId).orElseThrow(() -> new NotFoundException("Вакансия не найдена")));
    }

    public void deleteVacancyById(Long vacancyId) {
        vacancyRepo.deleteById(vacancyId);
    }

    public VacancyDto addVacancyForCurrentUser(VacancyDto vacancyDto) {
        var ownerId = AuthUtils.extractUserIdFromJwt();
        var owner = userService.getUserById(ownerId);
        var project = projectRepo.findById(vacancyDto.getProjectId()).orElse(null);
        var tags = tagService.getAllTagsByNames(vacancyDto.getTags().stream().map(TagDto::getName).toList());
        var profession = owner.getTags() != null && !owner.getTags().isEmpty() ? owner.getTags().iterator().next() : null;
        var vacancy = VacancyMapper.toVacancy(vacancyDto, owner, project, profession, new HashSet<>(tags));
        return VacancyMapper.toVacancyDto(vacancyRepo.save(vacancy));
    }

    public VacancyDto addVacancyForProject(VacancyDto vacancyDto, Long projectId) {
        vacancyDto.setProjectId(projectId);
        return addVacancyForCurrentUser(vacancyDto);
    }
}
