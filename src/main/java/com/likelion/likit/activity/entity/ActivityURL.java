package com.likelion.likit.activity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class ActivityURL {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(nullable = false)
    private String activityURL;

    @Builder
    public ActivityURL(Activity activity, String activityURL) {
        this.activity = activity;
        this.activityURL = activityURL;
    }
}
