package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Visit} entity.
 */
public class VisitDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate visitDate;

    private Long realizedBy;

    @NotNull
    private String description;

    @Lob
    private byte[] attachedFile;

    private String attachedFileContentType;
    private Boolean archivated;

    private BeneficiaryDTO beneficiary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public Long getRealizedBy() {
        return realizedBy;
    }

    public void setRealizedBy(Long realizedBy) {
        this.realizedBy = realizedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(byte[] attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getAttachedFileContentType() {
        return attachedFileContentType;
    }

    public void setAttachedFileContentType(String attachedFileContentType) {
        this.attachedFileContentType = attachedFileContentType;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public BeneficiaryDTO getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryDTO beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisitDTO)) {
            return false;
        }

        VisitDTO visitDTO = (VisitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, visitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisitDTO{" +
            "id=" + getId() +
            ", visitDate='" + getVisitDate() + "'" +
            ", realizedBy=" + getRealizedBy() +
            ", description='" + getDescription() + "'" +
            ", attachedFile='" + getAttachedFile() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", beneficiary=" + getBeneficiary() +
            "}";
    }
}
