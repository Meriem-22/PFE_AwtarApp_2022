package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EducationalInstitution.
 */
@Entity
@Table(name = "educational_institution")
public class EducationalInstitution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "adress")
    private String adress;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "educationalInstitution")
    @JsonIgnoreProperties(value = { "schoolLevel", "child", "educationalInstitution" }, allowSetters = true)
    private Set<TeachingCurriculum> teachingCurricula = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "personbirthPlaces", "personplaceOfResidences", "establishments", "educationalInstitutions" },
        allowSetters = true
    )
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EducationalInstitution id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EducationalInstitution name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return this.adress;
    }

    public EducationalInstitution adress(String adress) {
        this.setAdress(adress);
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public EducationalInstitution archivated(Boolean archivated) {
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
            this.teachingCurricula.forEach(i -> i.setEducationalInstitution(null));
        }
        if (teachingCurricula != null) {
            teachingCurricula.forEach(i -> i.setEducationalInstitution(this));
        }
        this.teachingCurricula = teachingCurricula;
    }

    public EducationalInstitution teachingCurricula(Set<TeachingCurriculum> teachingCurricula) {
        this.setTeachingCurricula(teachingCurricula);
        return this;
    }

    public EducationalInstitution addTeachingCurriculum(TeachingCurriculum teachingCurriculum) {
        this.teachingCurricula.add(teachingCurriculum);
        teachingCurriculum.setEducationalInstitution(this);
        return this;
    }

    public EducationalInstitution removeTeachingCurriculum(TeachingCurriculum teachingCurriculum) {
        this.teachingCurricula.remove(teachingCurriculum);
        teachingCurriculum.setEducationalInstitution(null);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public EducationalInstitution city(City city) {
        this.setCity(city);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducationalInstitution)) {
            return false;
        }
        return id != null && id.equals(((EducationalInstitution) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationalInstitution{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", adress='" + getAdress() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
