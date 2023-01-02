package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.DonationsReceived} entity.
 */
public class DonationsReceivedDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] urlPhoto;

    private String urlPhotoContentType;

    private Boolean archivated;

    private AuthorizingOfficerDTO authorizingOfficer;

    private String firstName;

    private String lastName;

    private LocalDate date;

    private String month;

    private Long number;

    public DonationsReceivedDTO() {}

    public DonationsReceivedDTO(Long id) {
        this.id = id;
    }

    public DonationsReceivedDTO(String month, Long number) {
        this.month = month;
        this.number = number;
    }

    public DonationsReceivedDTO(Long id, byte[] urlPhoto, String urlPhotoContentType, String firstName, String lastName, LocalDate date) {
        this.id = id;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationsReceivedDTO)) {
            return false;
        }

        DonationsReceivedDTO donationsReceivedDTO = (DonationsReceivedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donationsReceivedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationsReceivedDTO{" +
            "id=" + getId() +
            ", urlPhoto='" + getUrlPhoto() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", authorizingOfficer=" + getAuthorizingOfficer() +
            "}";
    }
}
