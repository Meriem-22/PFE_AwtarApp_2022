package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Visit.
 */
@Entity
@Table(name = "visit")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "realized_by")
    private Long realizedBy;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "attached_file")
    private byte[] attachedFile;

    @Column(name = "attached_file_content_type")
    private String attachedFileContentType;

    @Column(name = "archivated")
    private Boolean archivated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "donationdetails", "visits", "authorizingOfficer", "tutor" }, allowSetters = true)
    private Beneficiary beneficiary;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Visit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getVisitDate() {
        return this.visitDate;
    }

    public Visit visitDate(LocalDate visitDate) {
        this.setVisitDate(visitDate);
        return this;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public Long getRealizedBy() {
        return this.realizedBy;
    }

    public Visit realizedBy(Long realizedBy) {
        this.setRealizedBy(realizedBy);
        return this;
    }

    public void setRealizedBy(Long realizedBy) {
        this.realizedBy = realizedBy;
    }

    public String getDescription() {
        return this.description;
    }

    public Visit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getAttachedFile() {
        return this.attachedFile;
    }

    public Visit attachedFile(byte[] attachedFile) {
        this.setAttachedFile(attachedFile);
        return this;
    }

    public void setAttachedFile(byte[] attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getAttachedFileContentType() {
        return this.attachedFileContentType;
    }

    public Visit attachedFileContentType(String attachedFileContentType) {
        this.attachedFileContentType = attachedFileContentType;
        return this;
    }

    public void setAttachedFileContentType(String attachedFileContentType) {
        this.attachedFileContentType = attachedFileContentType;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public Visit archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Beneficiary getBeneficiary() {
        return this.beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Visit beneficiary(Beneficiary beneficiary) {
        this.setBeneficiary(beneficiary);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visit)) {
            return false;
        }
        return id != null && id.equals(((Visit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Visit{" +
            "id=" + getId() +
            ", visitDate='" + getVisitDate() + "'" +
            ", realizedBy=" + getRealizedBy() +
            ", description='" + getDescription() + "'" +
            ", attachedFile='" + getAttachedFile() + "'" +
            ", attachedFileContentType='" + getAttachedFileContentType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
