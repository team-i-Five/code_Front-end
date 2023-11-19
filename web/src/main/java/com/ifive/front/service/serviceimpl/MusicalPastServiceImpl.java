package com.ifive.front.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ifive.front.dto.MusicalPastDTO;
import com.ifive.front.entity.MusicalPast;
import com.ifive.front.repository.MusicalPastRepository;
import com.ifive.front.service.MusicalPastService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MusicalPastServiceImpl implements MusicalPastService{
    
    private MusicalPastRepository musicalPastRepository;

    @Autowired
    public MusicalPastServiceImpl(MusicalPastRepository musicalPastRepository){
        this.musicalPastRepository = musicalPastRepository;
    }

    @Override
    public List<MusicalPastDTO> getMusicalPastListOrderByEndDate(){
        // Pageable을 통해 0부터 50까지의 쿼리 데이터만 가져옴.
        Pageable pageable = PageRequest.of(0,50);

        List<MusicalPast> mpl = musicalPastRepository.findAllByOrderByEndDateDesc(pageable);
        // log.info("mpl 보여줘 : "+mpl);
        List<MusicalPastDTO> mplDto = new ArrayList<>();

        for(MusicalPast mp : mpl){
            mplDto.add(mp.toDTO());
        }

        return mplDto;
    }
}
