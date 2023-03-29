package com.likelion.likit.member.entity;

import lombok.Getter;

@Getter
public enum Major {
    전자공학부("ELECTRONIC_ENGINEERING"),
    정보전자전공("SEMICONDUCTOR_SYSTEM"),
    제어및로봇전공("CONTROL_AND_ROBOTICS"),
    전자통신전공("ELECTRONICS_AND_TELECOMMUNICATIONS"),
    전자및전파전공("ELECTRONICS_AND_ELECTRONICS"),
    전자IT융합전공("ELECTRONICS_IT_CONVERGENCE"),
    건축학부("ARCHITECTURE"),
    산업공학부("INDUSTRIAL_ENGINEERING"),
    산업경영공학전공("INDUSTRIAL_MANAGEMENT_ENGINEERING"),
    디자인공학전공("DESIGN_ENGINEERING"),
    화학소재공학부("CHEMICAL_MATERIALS_ENGINEERING"),
    고분자공학전공("POLYMER_SCIENCE_AND_ENGINEERING"),
    소재디자인공학전공("MATERIALS_DESIGN_ENGINEERING"),
    화학공학전공("CHEMICAL_ENGINEERING"),
    신소재공학부("MATERIALS_SCIENCE_AND_ENGINEERING"),
    기계공학과("MECHANICAL_ENGINEERING"),
    기계설계공학과("MECHANICAL_DESIGN_ENGINEERING"),
    기계시스템공학과("MECHANICAL_SYSTEM_ENGINEERING"),
    환경공학과("ENVIRONMENTAL_ENGINEERING"),
    IT융합학과("IT_CONVERGENCE"),
    수리빅데이터학과("MATHEMATICS_AND_BIG_DATA_SCIENCE"),
    화학생명과학과("CHEMISTRY_AND_BIO_SCIENCE"),
    경영학과("BUSINESS_ADMINISTRATION"),
    토목공학과("CIVIL_ENGINEERING"),
    컴퓨터공학과("COMPUTER_ENGINEERING"),
    컴퓨터소프트웨어공학과("COMPUTER_SOFTWARE_ENGINEERING"),
    인공지능공학과("ARTIFICIAL_INTELLIGENCE_ENGINEERING"),
    광시스템공학과("OPTICAL_ENGINEERING"),
    메디컬IT융합공학과("MEDICAL_IT_CONVERGENCE_ENGINEERING");
    private String major;

    Major(String major) {
        this.major = major;
    }
}
