//package com.likelion.likit.activity.dto;
//
//import com.likelion.likit.activity.entity.Activity;
//import com.likelion.likit.activity.entity.ActivityFile;
//import com.likelion.likit.activity.entity.ActivityURL;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor
//public class ActivityResDto {
//    private Long id;
//    private String icon;
//    private String title;
//    private String description;
//    private String startDate;
//    private String endDate;
//    private String achieve;
//    private String github;
//    private List<String> activityURLs;
//    private List<String> file;
//
//    public ActivityResDto(Activity activity) {
//        this.id = activity.getId();
//        this.icon = makeUrl(activity.getIcon());
//        this.title = activity.getTitle();
//        this.description = activity.getDescription();
//        this.startDate = activity.getStartDate();
//        this.endDate = activity.getEndDate();
//        this.achieve = activity.getAchieve();
//        this.github = activity.getGithub();
//        this.activityURLs = makeAct(activity.getActivityURLs());
//        this.file = makeList(activity.getActivityFiles());
//    }
//
//    private List<String> makeList(List<ActivityFile> fileList){
//        List<String> activityFiles = new ArrayList<>();
//        for(ActivityFile activityFile : fileList) {
//            activityFiles.add(makeUrl(activityFile));
//        }
//        return activityFiles;
//    }
//
//    @Value("${part4.upload.path}")
//    private String uploadPath;
//
//    private String makeUrl(ActivityFile file) {
//        return uploadPath + file.getFilePath();
//    }
//
//    private List<String> makeAct(List<ActivityURL> activityURLS) {
//        List<String> activityUrls = new ArrayList<>();
//        for (ActivityURL activityURL :)
//    }
//
//
//}
