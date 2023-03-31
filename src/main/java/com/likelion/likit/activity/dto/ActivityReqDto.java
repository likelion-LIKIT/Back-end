package com.likelion.likit.activity.dto;

import com.likelion.likit.activity.entity.Activity;
import com.likelion.likit.activity.entity.ActivityFile;
import com.likelion.likit.activity.entity.ActivityURL;
import com.likelion.likit.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class ActivityReqDto {
    String title;
    String description;
    String startDate;
    String endDate;
    String achieve;
    String github;
    List<String> activityUrl;

    public Activity toEntity(Member member, ActivityFile icon, ActivityFile thumbnail) {
        return Activity.builder()
                .icon(icon)
                .serviceThumbnail(thumbnail)
                .member(member)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .achieve(achieve)
                .github(github)
                .build();
    }

    public ActivityURL urlEntity(Activity activity, String activityURL) {
        return ActivityURL.builder()
                .activityURL(activityURL)
                .activity(activity)
                .build();
    }

}
