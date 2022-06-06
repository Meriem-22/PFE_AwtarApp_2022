package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TeachingCurriculum.
 */
@Entity
@Table(name = "teaching_curriculum")
public class TeachingCurriculum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "beginning_year", nullable = false)
    private String beginningYear;

    @NotNull
    @Column(name = "end_year", nullable = false)
    private String endYear;

    @Column(name = "annual_result")
    private Double annualResult;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    private Status result;

    @Column(name = "remarks")
    private String remarks;

    @Lob
    @Column(name = "attached_file")
    private byte[] attachedFile;

    @Column(name = "attached_file_content_type")
    private String attachedFileContentType;

    @Column(name = "archivated")
    private Boolean archivated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "teachingCurricula", "items" }, allowSetters = true)
    private SchoolLevel schoolLevel;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "teachingCurricula", "childProfile", "family" }, allowSetters = true)
    private Child child;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "teachingCurricula", "city" }, allowSetters = true)
    private EducationalInstitution educationalInstitution;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeachingCurriculum id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeginningYear() {
        return this.beginningYear;
    }

    public TeachingCurriculum beginningYear(String beginningYear) {
        this.setBeginningYear(beginningYear);
        return this;
    }

    public void setBeginningYear(String beginningYear) {
        this.beginningYear = beginningYear;
    }

    public String getEndYear() {
        return this.endYear;
    }

    public TeachingCurriculum endYear(String endYear) {
        this.setEndYear(endYear);
        return this;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public Double getAnnualResult() {
        return this.annualResult;
    }

    public TeachingCurriculum annualResult(Double annualResult) {
        this.setAnnualResult(annualResult);
        return this;
    }

    public void setAnnualResult(Double annualResult) {
        this.annualResult = annualResult;
    }

    public Status getResult() {
        return this.result;
    }

    public TeachingCurriculum result(Status result) {
        this.setResult(result);
        return this;
    }

    public void setResult(Status result) {
        this.result = result;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public TeachingCurriculum remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getAttachedFile() {
        return this.attachedFile;
    }

    public TeachingCurriculum attachedFile(byte[] attachedFile) {
        this.setAttachedFile(attachedFile);
        return this;
    }

    public void setAttachedFile(byte[] attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getAttachedFileContentType() {
        return this.attachedFileContentType;
    }

    public TeachingCurriculum attachedFileContentType(String attachedFileContentType) {
        this.attachedFileContentType = attachedFileContentType;
        return this;
    }

    public void setAttachedFileContentType(String attachedFileContentType) {
        this.attachedFileContentType = attachedFileContentType;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public TeachingCurriculum archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public SchoolLevel getSchoolLevel() {
        return this.schoolLevel;
    }

    public void setSchoolLevel(SchoolLevel schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public TeachingCurriculum schoolLevel(SchoolLevel schoolLevel) {
        this.setSchoolLevel(schoolLevel);
        return this;
    }

    public Child getChild() {
        return this.child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public TeachingCurriculum child(Child child) {
        this.setChild(child);
        return this;
    }

    public EducationalInstitution getEducationalInstitution() {
        return this.educationalInstitution;
    }

    public void setEducationalInstitution(EducationalInstitution educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    public TeachingCurriculum educationalInstitution(EducationalInstitution educationalInstitution) {
        this.setEducationalInstitution(educationalInstitution);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeachingCurriculum)) {
            return false;
        }
        return id != null && id.equals(((TeachingCurriculum) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeachingCurriculum{" +
            "id=" + getId() +
            ", beginningYear='" + getBeginningYear() + "'" +
            ", endYear='" + getEndYear() + "'" +
            ", annualResult=" + getAnnualResult() +
            ", result='" + getResult() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", attachedFile='" + getAttachedFile() + "'" +
            ", attachedFileContentType='" + getAttachedFileContentType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
