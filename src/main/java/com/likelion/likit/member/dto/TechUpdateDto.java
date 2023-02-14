package com.likelion.likit.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TechUpdateDto {
    List<String> tech;

    public TechUpdateDto(List<String> tech) {
        this.tech = tech;
    }
}
