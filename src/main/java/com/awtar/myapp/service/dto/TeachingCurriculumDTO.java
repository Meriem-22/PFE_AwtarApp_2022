package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.TeachingCurriculum} entity.
 */
public class TeachingCurriculumDTO implements Serializable {

    private Long id;

    @NotNull
    private String beginningYear;

    @NotNull
    private String endYear;

    private Double annualResult;

    @NotNull
    private Status result;

    private String remarks;

    @Lob
    private byte[] attachedFile;

    private String attachedFileContentType;

    private Boolean archivated;

    private SchoolLevelDTO schoolLevel;

    private ChildDTO child;

    private EducationalInstitutionDTO educationalInstitution;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public SchoolLevelDTO getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(SchoolLevelDTO schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public ChildDTO getChild() {
        return child;
    }

    public void setChild(ChildDTO child) {
        this.child = child;
    }

    public EducationalInstitutionDTO getEducationalInstitution() {
        return educationalInstitution;
    }

    public void setEducationalInstitution(EducationalInstitutionDTO educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeachingCurriculumDTO)) {
            return false;
        }

        TeachingCurriculumDTO teachingCurriculumDTO = (TeachingCurriculumDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teachingCurriculumDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeachingCurriculumDTO{" +
            "id=" + getId() +
            ", beginningYear='" + getBeginningYear() + "'" +
            ", endYear='" + getEndYear() + "'" +
            ", annualResult=" + getAnnualResult() +
            ", result='" + getResult() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", attachedFile='" + getAttachedFile() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", schoolLevel=" + getSchoolLevel() +
            ", child=" + getChild() +
            ", educationalInstitution=" + getEducationalInstitution() +
            "}";
    }
}
