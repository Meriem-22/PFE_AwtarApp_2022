package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A SchoolLevel.
 */
@Entity
@Table(name = "school_level")
public class SchoolLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "school_level", nullable = false, unique = true)
    private String schoolLevel;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "schoolLevel")
    @JsonIgnoreProperties(value = { "schoolLevel", "child", "educationalInstitution" }, allowSetters = true)
    private Set<TeachingCurriculum> teachingCurricula = new HashSet<>();

    @OneToMany(mappedBy = "schoolLevel")
    @JsonIgnoreProperties(value = { "item", "schoolLevel" }, allowSetters = true)
    private Set<SchoolLevelItem> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SchoolLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolLevel() {
        return this.schoolLevel;
    }

    public SchoolLevel schoolLevel(String schoolLevel) {
        this.setSchoolLevel(schoolLevel);
        return this;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public SchoolLevel archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<TeachingCurriculum> getTeachingCurricula() {
        return this.teachingCurricula;
    }

    public void setTeachingCurricula(Set<TeachingCurriculum> teachingCurricula) {
        if (this.teachingCurricula != null) {
            this.teachingCurricula.forEach(i -> i.setSchoolLevel(null));
        }
        if (teachingCurricula != null) {
            teachingCurricula.forEach(i -> i.setSchoolLevel(this));
        }
        this.teachingCurricula = teachingCurricula;
    }

    public SchoolLevel teachingCurricula(Set<TeachingCurriculum> teachingCurricula) {
        this.setTeachingCurricula(teachingCurricula);
        return this;
    }

    public SchoolLevel addTeachingCurriculum(TeachingCurriculum teachingCurriculum) {
        this.teachingCurricula.add(teachingCurriculum);
        teachingCurriculum.setSchoolLevel(this);
        return this;
    }

    public SchoolLevel removeTeachingCurriculum(TeachingCurriculum teachingCurriculum) {
        this.teachingCurricula.remove(teachingCurriculum);
        teachingCurriculum.setSchoolLevel(null);
        return this;
    }

    public Set<SchoolLevelItem> getItems() {
        return this.items;
    }

    public void setItems(Set<SchoolLevelItem> schoolLevelItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setSchoolLevel(null));
        }
        if (schoolLevelItems != null) {
            schoolLevelItems.forEach(i -> i.setSchoolLevel(this));
        }
        this.items = schoolLevelItems;
    }

    public SchoolLevel items(Set<SchoolLevelItem> schoolLevelItems) {
        this.setItems(schoolLevelItems);
        return this;
    }

    public SchoolLevel addItem(SchoolLevelItem schoolLevelItem) {
        this.items.add(schoolLevelItem);
        schoolLevelItem.setSchoolLevel(this);
        return this;
    }

    public SchoolLevel removeItem(SchoolLevelItem schoolLevelItem) {
        this.items.remove(schoolLevelItem);
        schoolLevelItem.setSchoolLevel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolLevel)) {
            return false;
        }
        return id != null && id.equals(((SchoolLevel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchoolLevel{" +
            "id=" + getId() +
            ", schoolLevel='" + getSchoolLevel() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
