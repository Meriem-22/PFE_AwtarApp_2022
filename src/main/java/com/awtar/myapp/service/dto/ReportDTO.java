package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Report} entity.
 */
public class ReportDTO implements Serializable {

    private Long id;

    @NotNull
    private String nature;

    @NotNull
    private LocalDate date;

    @Lob
    private byte[] urlEnclosed;

    private String urlEnclosedContentType;
    private Boolean archivated;

    private EstablishmentDTO establishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getUrlEnclosed() {
        return urlEnclosed;
    }

    public void setUrlEnclosed(byte[] urlEnclosed) {
        this.urlEnclosed = urlEnclosed;
    }

    public String getUrlEnclosedContentType() {
        return urlEnclosedContentType;
    }

    public void setUrlEnclosedContentType(String urlEnclosedContentType) {
        this.urlEnclosedContentType = urlEnclosedContentType;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public EstablishmentDTO getEstablishment() {
        return establishment;
    }

    public void setEstablishment(EstablishmentDTO establishment) {
        this.establishment = establishment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportDTO)) {
            return false;
        }

        ReportDTO reportDTO = (ReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportDTO{" +
            "id=" + getId() +
            ", nature='" + getNature() + "'" +
            ", date='" + getDate() + "'" +
            ", urlEnclosed='" + getUrlEnclosed() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", establishment=" + getEstablishment() +
            "}";
    }
}
