package gr.aueb.cf.mutu.controller.dto;

import java.util.List;

public class UpdateUserRequestDto {

    private Integer weight;
    private String bio;
    private List<Long> interestsIds;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Long> getInterestsIds() {
        return interestsIds;
    }

    public void setInterestsIds(List<Long> interestsIds) {
        this.interestsIds = interestsIds;
    }
}
