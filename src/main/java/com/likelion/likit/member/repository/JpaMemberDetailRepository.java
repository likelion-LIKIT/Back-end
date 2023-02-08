package com.likelion.likit.member.repository;

import com.likelion.likit.member.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaMemberDetailRepository extends JpaRepository<MemberDetail,Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.studentName = :studentName WHERE c.memberId = :memberId ")
    void updateStudentName(Optional<String> updateStudentName, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.phoneNumber = :phoneNumber WHERE c.memberId = :memberId ")
    void updatePhoneNumber(Optional<String> updatePhoneNumber, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.description = :description WHERE c.memberId = :memberId ")
    void updateDescription(Optional<String> updateDescription, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.grade = :grade WHERE c.memberId = :memberId ")
    void updateGrade(Optional<Grade> updateGrade, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.major = :major WHERE c.memberId = :memberId ")
    void updateMajor(Optional<Major> updateMajor, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.track = :track WHERE c.memberId = :memberId ")
    void updateTrack(Optional<Track> updateTrack, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.track = :track WHERE c.memberId = :memberId ")
    void updateTechStack(Optional<TechStack> updateTechStack, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.likelionEmail = :likelionEmail WHERE c.memberId = :memberId ")
    void updateLikelionEmail(Optional<String> updateLikelionEmail, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.email = :email WHERE c.memberId = :memberId ")
    void updateEmail(Optional<String> updateEmail, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.term = :term WHERE c.memberId = :memberId ")
    void updateTerm(Optional<Integer> updateTerm, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.position = :position WHERE c.memberId = :memberId ")
    void updatePosition(Optional<Position> updatePosition, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.birth = :birth WHERE c.memberId = :memberId ")
    void updateBirth(Optional<String> updateBirth, Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.github = :github WHERE c.memberId = :memberId ")
    void updateGithub(Optional<String> updateGithub, Long memberId);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.updateDate = :updateDate WHERE c.memberId = :memberId ")
    void updateUpdateDate(String updateUpdateDate, Long memberId);



}
