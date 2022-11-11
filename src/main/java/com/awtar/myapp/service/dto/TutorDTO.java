package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Tutor} entity.
 */
public class TutorDTO implements Serializable {

    private Long id;

    private String activity;

    private String manager;

    private String managerCin;

    private ProfileDTO profile;

    private String firstName;

    private String lastName;

    private String address;

    private byte[] urlPhoto;

    private String urlPhotoContentType;

    public TutorDTO() {}

    public TutorDTO(
        Long id,
        String firstName,
        String lastName,
        String address,
        byte[] urlPhoto,
        String urlPhotoContentType,
        String activity
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.activity = activity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerCin() {
        return managerCin;
    }

    public void setManagerCin(String managerCin) {
        this.managerCin = managerCin;
    }

    public ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(byte[] urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlPhotoContentType() {
        return urlPhotoContentType;
    }

    public void setUrlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TutorDTO)) {
            return false;
        }

        TutorDTO tutorDTO = (TutorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tutorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorDTO{" +
            "id=" + getId() +
            ", activity='" + getActivity() + "'" +
            ", manager='" + getManager() + "'" +
            ", managerCin='" + getManagerCin() + "'" +
            "}";
    }
}
