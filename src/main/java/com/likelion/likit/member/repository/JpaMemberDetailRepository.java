package com.likelion.likit.member.repository;

import com.likelion.likit.member.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface JpaMemberDetailRepository extends JpaRepository<MemberDetail,Long> {

    Optional<MemberDetail> findByMemberId(Long memberId);
    Optional<MemberDetail> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.studentName = :studentName WHERE c.id = :id ")
    void updateStudentName(String studentName, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.phoneNumber = :phoneNumber WHERE c.id = :id ")
    void updatePhoneNumber(String phoneNumber, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.description = :description WHERE c.id = :id ")
    void updateDescription(String description, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.grade = :grade WHERE c.id = :id ")
    void updateGrade(Grade grade, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.major = :major WHERE c.id = :id ")
    void updateMajor(Major major, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.track = :track WHERE c.id = :id ")
    void updateTrack(Track track, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.likelionEmail = :likelionEmail WHERE c.id = :id ")
    void updateLikelionEmail(String likelionEmail, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.email = :email WHERE c.id = :id ")
    void updateEmail(String email, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.term = :term WHERE c.id = :id ")
    void updateTerm(Integer term, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.position = :position WHERE c.id = :id ")
    void updatePosition(Position position, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.birth = :birth WHERE c.id = :id ")
    void updateBirth(String birth, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.github = :github WHERE c.id = :id ")
    void updateGithub(String github, Long id);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE MemberDetail c SET c.updateDate = :updateDate WHERE c.id = :id ")
    void updateDate(String  updateDate, Long id);



}
