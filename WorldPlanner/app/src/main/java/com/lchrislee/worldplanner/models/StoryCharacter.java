package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orm.SugarRecord;

import java.io.Serializable;

public class StoryCharacter extends SugarRecord implements Serializable, StoryElement{

    private StoryWorld world;

    // Basic features.
    private String name;
    private String description;
    private String nickname;
    private String gender;
    private String imagePath; // In SugarORM: image_path;
    private int age;
    private int height;
    private int weight;

    // Head features.
    private String hair;
    private String eyes;
    private String ears;
    private String nose;
    private String mouth;
    private String neck;

    // Torso features.
    private String shoulders;
    private String chest;
    private String stomach;
    private String back;

    // Arm features
    private String upperLeftArm;
    private String upperRightArm;
    private String lowerLeftArm;
    private String lowerRightArm;
    private String handLeft;
    private String handRight;

    // Leg features
    private String upperLeftLeg;
    private String upperRightLeg;
    private String lowerLeftLeg;
    private String lowerRightLeg;
    private String footLeft;
    private String footRight;

    public StoryCharacter() {
        name = "";
        description = "";
        imagePath = "";
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String getImage() {
        return imagePath;
    }

    @Override
    public void setImage(@NonNull String path) {
        imagePath = path;
    }

    public @Nullable String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public @Nullable String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWorld(StoryWorld world) {
        this.world = world;
    }

    public StoryWorld getWorld() {
        return world;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getEars() {
        return ears;
    }

    public void setEars(String ears) {
        this.ears = ears;
    }

    public String getNose() {
        return nose;
    }

    public void setNose(String nose) {
        this.nose = nose;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getShoulders() {
        return shoulders;
    }

    public void setShoulders(String shoulders) {
        this.shoulders = shoulders;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getStomach() {
        return stomach;
    }

    public void setStomach(String stomach) {
        this.stomach = stomach;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getUpperLeftArm() {
        return upperLeftArm;
    }

    public void setUpperLeftArm(String upperLeftArm) {
        this.upperLeftArm = upperLeftArm;
    }

    public String getUpperRightArm() {
        return upperRightArm;
    }

    public void setUpperRightArm(String upperRightArm) {
        this.upperRightArm = upperRightArm;
    }

    public String getLowerLeftArm() {
        return lowerLeftArm;
    }

    public void setLowerLeftArm(String lowerLeftArm) {
        this.lowerLeftArm = lowerLeftArm;
    }

    public String getLowerRightArm() {
        return lowerRightArm;
    }

    public void setLowerRightArm(String lowerRightArm) {
        this.lowerRightArm = lowerRightArm;
    }

    public String getHandLeft() {
        return handLeft;
    }

    public void setHandLeft(String handLeft) {
        this.handLeft = handLeft;
    }

    public String getHandRight() {
        return handRight;
    }

    public void setHandRight(String handRight) {
        this.handRight = handRight;
    }

    public String getUpperLeftLeg() {
        return upperLeftLeg;
    }

    public void setUpperLeftLeg(String upperLeftLeg) {
        this.upperLeftLeg = upperLeftLeg;
    }

    public String getUpperRightLeg() {
        return upperRightLeg;
    }

    public void setUpperRightLeg(String upperRightLeg) {
        this.upperRightLeg = upperRightLeg;
    }

    public String getLowerLeftLeg() {
        return lowerLeftLeg;
    }

    public void setLowerLeftLeg(String lowerLeftLeg) {
        this.lowerLeftLeg = lowerLeftLeg;
    }

    public String getLowerRightLeg() {
        return lowerRightLeg;
    }

    public void setLowerRightLeg(String lowerRightLeg) {
        this.lowerRightLeg = lowerRightLeg;
    }

    public String getFootLeft() {
        return footLeft;
    }

    public void setFootLeft(String footLeft) {
        this.footLeft = footLeft;
    }

    public String getFootRight() {
        return footRight;
    }

    public void setFootRight(String footRight) {
        this.footRight = footRight;
    }
}
