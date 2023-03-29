package com.likelion.likit.member.dto;

import com.likelion.likit.member.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PositionUpdateDto {
    List<Category> position;

    public PositionUpdateDto(List<Category> position) {
        this.position = position;
    }
}
