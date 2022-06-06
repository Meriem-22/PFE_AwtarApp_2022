package com.awtar.myapp.service.dto;

import java.io.Serializable;
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
