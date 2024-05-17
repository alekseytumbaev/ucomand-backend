package com.example.ucomandbackend.profession;

import com.example.ucomandbackend.error_handling.NotFoundException;
import com.example.ucomandbackend.profession.api.ProfessionDto;
import com.example.ucomandbackend.util.PageableDto;
import com.example.ucomandbackend.util.PageableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionService {

    private final ProfessionRepository professionRepo;

    /**
     * @throws org.springframework.dao.DataIntegrityViolationException название уже существует
     */
    @Transactional
    public ProfessionDto addProfession(ProfessionDto professionDto) {
        professionDto.setId(null);
        var profession = professionRepo.save(ProfessionMapper.toProfession(professionDto));
        return ProfessionMapper.toProfessionDto(profession);
    }

    /**
     * @throws org.springframework.dao.DataIntegrityViolationException название уже существует
     */
    @Transactional
    public ProfessionDto updateProfessionById(Long professionId, ProfessionDto professionDto) {
        professionDto.setId(professionId);
        var profession = professionRepo.save(ProfessionMapper.toProfession(professionDto));
        return ProfessionMapper.toProfessionDto(profession);
    }

    public void deleteProfessionById(Long professionId) {
        professionRepo.deleteById(professionId);
    }

    /**
     * @throws NotFoundException
     */
    @Transactional(readOnly = true)
    public ProfessionDto getProfessionById(Long professionId) {
        var profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new NotFoundException("Профессия не найдена"));

        return ProfessionMapper.toProfessionDto(profession);
    }

    @Transactional(readOnly = true)
    public List<ProfessionDto> getAllProfessions(PageableDto pageableDto) {
        return professionRepo.findAll(PageableMapper.toPageable(pageableDto))
                .map(ProfessionMapper::toProfessionDto)
                .getContent();
    }
}
