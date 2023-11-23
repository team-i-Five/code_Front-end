package com.ifive.front.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifive.front.dto.MusicalPresentDTO;
import com.ifive.front.entity.MusicalPresent;
import com.ifive.front.service.MusicalPresentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MusicalPresentController {

    // tagName 맵을 클래스 변수로 선언
    private static final Map<String, String> tagName = new HashMap<>();

    // 클래스 변수를 정적 블록에서 초기화
    static {
        tagName.put("love", "사랑");
        tagName.put("opera", "오페라");
        tagName.put("child", "아이");
        tagName.put("friend", "친구");
        tagName.put("art", "예술");
        tagName.put("magic", "마법");
        tagName.put("person", "사람");
        tagName.put("history", "역사");
        tagName.put("fear", "공포");
        tagName.put("laugh", "웃음");
        tagName.put("sad", "슬픔");
    }

    @Autowired
    private MusicalPresentService musicalPresentService;

    @Autowired
    public MusicalPresentController(MusicalPresentService musicalPresentService) {
        this.musicalPresentService = musicalPresentService;
    }

    @GetMapping("/ml/{id}")
    public String drawPresent(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
        List<MusicalPresentDTO> musicalPresents = musicalPresentService.getPresentDTOsbyIdFromML(id);

        log.info("ㅇㅈㅇㅈㅇㅈ : " + musicalPresents);

        // 데이터를 RedirectAttributes에 추가
        redirectAttributes.addFlashAttribute("presentResult", musicalPresents);
        redirectAttributes.addFlashAttribute("id", id);

        // 리다이렉트 URL
        return "redirect:/result";
    }

    
    @GetMapping("/result")
    public String resultShow(@ModelAttribute("id") String id,
                             @ModelAttribute("presentResult") List<MusicalPresentDTO> musicalPresents,
                             Model model) {
        log.info("Help");
    
        log.info("dwdw" + id);
        log.info("dwdwdwd    " + musicalPresents.get(0));
    
        model.addAttribute("dwkpdw", id);
        model.addAttribute("presentResults", musicalPresents);
        
        return "result";
    }
    
}
