package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.Gender;
import com.awtar.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Child} entity.
 */
public class ChildDTO implements Serializable {

    private Long id;

    private FamilyDTO family;

    private ProfileDTO profile;

    private ProfileDTO authorizingOfficer;

    private ProfileDTO tutor;

    private String beginningYear;

    private String endYear;

    private Double annualResult;

    private Status result;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String familyName;

    private Long idProfile;

    private String schoolLevel;

    private byte[] urlPhoto;

    private String urlPhotoContentType;

    public ChildDTO() {}

    public ChildDTO(
        Long id,
        Long idProfile,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate dateOfBirth,
        String familyName,
        String beginningYear,
        String endYear,
        Double annualResult,
        Status result,
        String schoolLevel,
        byte[] urlPhoto,
        String urlPhotoContentType
    ) {
        this.id = id;
        this.idProfile = idProfile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.familyName = familyName;
        this.beginningYear = beginningYear;
        this.endYear = endYear;
        this.annualResult = annualResult;
        this.result = result;
        this.schoolLevel = schoolLevel;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public ChildDTO(Long id, String beginningYear, String endYear, Double annualResult, Status result, String schoolLevel) {
        this.id = id;
        this.beginningYear = beginningYear;
        this.endYear = endYear;
        this.annualResult = annualResult;
        this.result = result;
        this.schoolLevel = schoolLevel;
    }

    public ChildDTO(
        Long id,
        Long idProfile,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate dateOfBirth,
        String familyName,
        byte[] urlPhoto,
        String urlPhotoContentType
    ) {
        this.id = id;
        this.idProfile = idProfile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.familyName = familyName;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public ChildDTO(
        Long id,
        Long idProfile,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate dateOfBirth,
        byte[] urlPhoto,
        String urlPhotoContentType
    ) {
        this.id = id;
        this.idProfile = idProfile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FamilyDTO getFamily() {
        return family;
    }

    public void setFamily(FamilyDTO family) {
        this.family = family;
    }

    public ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }

    public ProfileDTO getAuthorizingOfficer() {
        return authorizingOfficer;
    }

    public void setAuthorizingOfficer(ProfileDTO authorizingOfficer) {
        this.authorizingOfficer = authorizingOfficer;
    }

    public ProfileDTO getTutor() {
        return tutor;
    }

    public void setTutor(ProfileDTO tutor) {
        this.tutor = tutor;
    }

    public String getBeginningYear() {
        return beginningYear;
    }

    public void setBeginningYear(String beginningYear) {
        this.beginningYear = beginningYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public Double getAnnualResult() {
        return annualResult;
    }

    public void setAnnualResult(Double annualResult) {
        this.annualResult = annualResult;
    }

    public Status getResult() {
        return result;
    }

    public void setResult(Status result) {
        this.result = result;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Long getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(Long idProfile) {
        this.idProfile = idProfile;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
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
        if (!(o instanceof ChildDTO)) {
            return false;
        }

        ChildDTO childDTO = (ChildDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, childDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildDTO{" +
            "id=" + getId() +
            ", family=" + getFamily() +
            "}";
    }
}
