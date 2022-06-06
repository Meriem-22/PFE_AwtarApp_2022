package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.Gender;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Profile} entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String firstNameArabic;

    private String lastNameArabic;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate dateOfBirth;

    private String cin;

    @Lob
    private byte[] urlPhoto;

    private String urlPhotoContentType;
    private String address;

    private String phone;

    private String email;

    @Lob
    private byte[] urlCinAttached;

    private String urlCinAttachedContentType;
    private Boolean archivated;

    private ParentDTO parent;

    private ChildDTO child;

    private AuthorizingOfficerDTO authorizingOfficer;

    private TutorDTO tutor;

    private CityDTO birthPlace;

    private CityDTO placeOfResidence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstNameArabic() {
        return firstNameArabic;
    }

    public void setFirstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
    }

    public String getLastNameArabic() {
        return lastNameArabic;
    }

    public void setLastNameArabic(String lastNameArabic) {
        this.lastNameArabic = lastNameArabic;
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getUrlCinAttached() {
        return urlCinAttached;
    }

    public void setUrlCinAttached(byte[] urlCinAttached) {
        this.urlCinAttached = urlCinAttached;
    }

    public String getUrlCinAttachedContentType() {
        return urlCinAttachedContentType;
    }

    public void setUrlCinAttachedContentType(String urlCinAttachedContentType) {
        this.urlCinAttachedContentType = urlCinAttachedContentType;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public ParentDTO getParent() {
        return parent;
    }

    public void setParent(ParentDTO parent) {
        this.parent = parent;
    }

    public ChildDTO getChild() {
        return child;
    }

    public void setChild(ChildDTO child) {
        this.child = child;
    }

    public AuthorizingOfficerDTO getAuthorizingOfficer() {
        return authorizingOfficer;
    }

    public void setAuthorizingOfficer(AuthorizingOfficerDTO authorizingOfficer) {
        this.authorizingOfficer = authorizingOfficer;
    }

    public TutorDTO getTutor() {
        return tutor;
    }

    public void setTutor(TutorDTO tutor) {
        this.tutor = tutor;
    }

    public CityDTO getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(CityDTO birthPlace) {
        this.birthPlace = birthPlace;
    }

    public CityDTO getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(CityDTO placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileDTO)) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstNameArabic='" + getFirstNameArabic() + "'" +
            ", lastNameArabic='" + getLastNameArabic() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", cin='" + getCin() + "'" +
            ", urlPhoto='" + getUrlPhoto() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", urlCinAttached='" + getUrlCinAttached() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", parent=" + getParent() +
            ", child=" + getChild() +
            ", authorizingOfficer=" + getAuthorizingOfficer() +
            ", tutor=" + getTutor() +
            ", birthPlace=" + getBirthPlace() +
            ", placeOfResidence=" + getPlaceOfResidence() +
            "}";
    }
}
