package com.ifive.front.service.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifive.front.dto.MusicalDTO;
import com.ifive.front.dto.MusicalJsonParseDTO;
import com.ifive.front.entity.Musical;
import com.ifive.front.repository.MusicalRepository;
import com.ifive.front.service.MusicalService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MusicalServiceImpl implements MusicalService {
    private final MusicalRepository musicalRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MusicalServiceImpl(MusicalRepository musicalRepository, ObjectMapper objectMapper) {
        this.musicalRepository = musicalRepository;
        this.objectMapper = objectMapper;
    }

    // objectMapper를 통해, jsonStr을 List<Musical>에 매핑합니다. 이후 List 반환
    @Override
    public List<Musical> getMusicalsFromJsonString(String jsonString) {
        try {
            List<Musical> musicals = objectMapper.readValue(jsonString, new TypeReference<List<Musical>>() {});
            //log.info("ObjectMapper로 매핑한 musicals[0] : {}", musicals.get(0));
            return musicals;

        } catch (Exception e) {
            log.error("log error : {}",e);
            List<Musical> nullList = new ArrayList<>();
            return nullList;
        }
    }

    @Override
    public List<Musical> getMusicalsFromJsonFile(String jsonPath) {
        try {
            ClassPathResource resource = new ClassPathResource(jsonPath);
            List<Musical> musicals = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Musical>>() {});
            //log.info("ObjectMapper로 매핑한 musicals[0] : {}", musicals.get(0));
            return musicals;
        } catch (IOException e) {
            e.printStackTrace();
            List<Musical> nullList = new ArrayList<>();
            return nullList;
        }
    }

    @Override
    public void saveMusicalFromJson(String jsonPath) throws Exception {
        // JSON 파일을 Classpath에서 읽어옴
        ClassPathResource resource = new ClassPathResource(jsonPath);

        // JSON 파일을 MusicalDTO 리스트로 매핑
        List<MusicalJsonParseDTO> musicalJsonDTOs = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<MusicalJsonParseDTO>>() {}
        );

        // MusicalListDTO를 Muscial로 분리해서 DB에 저장
        for (MusicalJsonParseDTO musicalJsonDTO : musicalJsonDTOs) {
            Musical musical = musicalJsonDTO.toEntity();
            musicalRepository.save(musical);
        }
    }

    public List<MusicalDTO> getAllMusicals() {
        List<Musical> musicalEntities = musicalRepository.findAll();
        List<MusicalDTO> musicalDTOs = new ArrayList<>();
        for (Musical musicalEntity : musicalEntities) {
            musicalDTOs.add(musicalEntity.toDTO());
        }

        return musicalDTOs;
    }

    // @Override
    // public MusicalDTO getMusicalById(int musicalId) {
    //     // MusicalRepository를 통해 해당 ID에 해당하는 Musical 엔티티를 찾습니다.
    //     Optional<Musical> musicalEntityOptional = musicalRepository.findById((long) musicalId);
    
    //     // Musical 엔티티가 존재하면 DTO로 변환하여 반환합니다.
    //     if (musicalEntityOptional.isPresent()) {
    //         return musicalEntityOptional.get().toDTO();
    //     } else {
    //         // 해당 ID에 해당하는 Musical이 없으면 null을 반환하거나 예외를 던지도록 처리할 수 있습니다.
    //         // 여기서는 null을 반환하도록 하겠습니다.
    //         return null;
    //     }
    // }
    
    //     // musicalId를 이용하여 DB에서 Musical 객체를 조회
    // @Override
    // public Musical getMusicalFromDB(String musicalId) {
    //     Optional<Musical> optionalMusical  = musicalRepository.findByMusicalId(musicalId);

    //     // 뮤지컬을 반환하고 없다면 null리턴
    //     return optionalMusical.orElseGet(() -> {
    //         Musical dummyMusical = new Musical(0,null,null,null,null,null,null,null,null,null,null);
    //         return dummyMusical;
    //     });
    //  }

    // @Override
    // public List<MusicalIdWithRankDTO> getMusicalIdWithRankListDTOFromJson(String jsonResponse) {
    //     try {
    //         return objectMapper.readValue(jsonResponse, new TypeReference<List<MusicalIdWithRankDTO>>() {});
    //     } catch (IOException e) {
    //         // 예외 처리
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // @Override
    // Map<Integer, Musical> getMusicalRankMapFromJson(String jsonResponse) {
    //     try {
    //         List<MusicalIDWithRankDTO> dtoList = objectMapper.readValue(jsonResponse, new TypeReference<List<MusicalIDWithRankDTO>>() {});

    //         return dtoList.stream()
    //                 .collect(Collectors.toMap(
    //                         MusicalIDWithRankDTO::getRank,
    //                         dto -> getMusicalFromDB(dto.getMusicalID())
    //                 ));
    //     } catch (IOException e) {
    //         // 예외 처리
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
}