package com.likelion.likit.member.entity;

import lombok.Getter;

@Getter
public enum TechStack {
    JAVA("JAVA"),
    PYTHON("PYTHON"),
    C("C"),
    CPP("CPP"),
    SQL("SQL"),
    MYSQL("MYSQL"),
    HTML("HTML"),
    CSS("CSS"),
    REACT("REACT"),
    JAVASCRIPT("JAVASCRIPT"),
    DOCKER("DOCKER"),
    AWS("AWS"),
    CSHARP("CSHARP"),
    DJANGO("DJANGO"),
    SPRING("SPRING"),
    SPRINGBOOT("SPRINGBOOT"),
    VUE("VUE"),
    GO("GO"),
    FLASK("FLASK"),
    SVELTE("SVELTE"),
    NEXTJS("NEXTJS"),
    TYPESCRIPT("TYPESCRIPT"),
    FIGMA("FIGMA"),
    GIT("GIT"),
    SWIFT("SWIFT"),
    KOTLIN("KOTLIN"),
    KUBERNETES("KUBERNETES");
    private String teckStack;

    TechStack(String teckStack) {
        this.teckStack = teckStack;
    }

}
