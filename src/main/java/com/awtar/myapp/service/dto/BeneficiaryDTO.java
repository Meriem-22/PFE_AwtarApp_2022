package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.Beneficiaries;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Beneficiary} entity.
 */
public class BeneficiaryDTO implements Serializable {

    private Long id;

    private String beneficiaryReference;

    @NotNull
    private Beneficiaries beneficiaryType;

    private Boolean archivated;

    private AuthorizingOfficerDTO authorizingOfficer;

    private TutorDTO tutor;

    private Long idContributor;

    private String firstName;

    private String lastName;

    private byte[] urlPhoto;

    private String urlPhotoContentType;

    private String familyName;

    private String name;

    private String activity;

    public BeneficiaryDTO() {}

    public BeneficiaryDTO(Long id, String familyName) {
        this.id = id;
        this.familyName = familyName;
    }

    public BeneficiaryDTO(Long id, String firstName, String lastName, byte[] urlPhoto, String urlPhotoContentType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public BeneficiaryDTO(Long id, String name, String activity) {
        this.id = id;
        this.name = name;
        this.activity = activity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeneficiaryReference() {
        return beneficiaryReference;
    }

    public void setBeneficiaryReference(String beneficiaryReference) {
        this.beneficiaryReference = beneficiaryReference;
    }

    public Beneficiaries getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(Beneficiaries beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
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

    public Long getIdContributor() {
        return idContributor;
    }

    public void setIdContributor(Long idContributor) {
        this.idContributor = idContributor;
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

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BeneficiaryDTO)) {
            return false;
        }

        BeneficiaryDTO beneficiaryDTO = (BeneficiaryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, beneficiaryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BeneficiaryDTO{" +
            "id=" + getId() +
            ", beneficiaryReference='" + getBeneficiaryReference() + "'" +
            ", beneficiaryType='" + getBeneficiaryType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", authorizingOfficer=" + getAuthorizingOfficer() +
            ", tutor=" + getTutor() +
            "}";
    }
}
