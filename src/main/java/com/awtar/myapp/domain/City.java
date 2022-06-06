package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A City.
 */
@Entity
@Table(name = "city")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "governorate")
    private String governorate;

    @Column(name = "is_governorate")
    private Boolean isGovernorate;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "birthPlace")
    @JsonIgnoreProperties(
        value = { "parent", "child", "authorizingOfficer", "tutor", "statusOfHealths", "birthPlace", "placeOfResidence" },
        allowSetters = true
    )
    private Set<Profile> personbirthPlaces = new HashSet<>();

    @OneToMany(mappedBy = "placeOfResidence")
    @JsonIgnoreProperties(
        value = { "parent", "child", "authorizingOfficer", "tutor", "statusOfHealths", "birthPlace", "placeOfResidence" },
        allowSetters = true
    )
    private Set<Profile> personplaceOfResidences = new HashSet<>();

    @OneToMany(mappedBy = "city")
    @JsonIgnoreProperties(value = { "reports", "establishmentType", "city" }, allowSetters = true)
    private Set<Establishment> establishments = new HashSet<>();

    @OneToMany(mappedBy = "city")
    @JsonIgnoreProperties(value = { "teachingCurricula", "city" }, allowSetters = true)
    private Set<EducationalInstitution> educationalInstitutions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public City id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public City name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGovernorate() {
        return this.governorate;
    }

    public City governorate(String governorate) {
        this.setGovernorate(governorate);
        return this;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public Boolean getIsGovernorate() {
        return this.isGovernorate;
    }

    public City isGovernorate(Boolean isGovernorate) {
        this.setIsGovernorate(isGovernorate);
        return this;
    }

    public void setIsGovernorate(Boolean isGovernorate) {
        this.isGovernorate = isGovernorate;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public City archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<Profile> getPersonbirthPlaces() {
        return this.personbirthPlaces;
    }

    public void setPersonbirthPlaces(Set<Profile> profiles) {
        if (this.personbirthPlaces != null) {
            this.personbirthPlaces.forEach(i -> i.setBirthPlace(null));
        }
        if (profiles != null) {
            profiles.forEach(i -> i.setBirthPlace(this));
        }
        this.personbirthPlaces = profiles;
    }

    public City personbirthPlaces(Set<Profile> profiles) {
        this.setPersonbirthPlaces(profiles);
        return this;
    }

    public City addPersonbirthPlace(Profile profile) {
        this.personbirthPlaces.add(profile);
        profile.setBirthPlace(this);
        return this;
    }

    public City removePersonbirthPlace(Profile profile) {
        this.personbirthPlaces.remove(profile);
        profile.setBirthPlace(null);
        return this;
    }

    public Set<Profile> getPersonplaceOfResidences() {
        return this.personplaceOfResidences;
    }

    public void setPersonplaceOfResidences(Set<Profile> profiles) {
        if (this.personplaceOfResidences != null) {
            this.personplaceOfResidences.forEach(i -> i.setPlaceOfResidence(null));
        }
        if (profiles != null) {
            profiles.forEach(i -> i.setPlaceOfResidence(this));
        }
        this.personplaceOfResidences = profiles;
    }

    public City personplaceOfResidences(Set<Profile> profiles) {
        this.setPersonplaceOfResidences(profiles);
        return this;
    }

    public City addPersonplaceOfResidence(Profile profile) {
        this.personplaceOfResidences.add(profile);
        profile.setPlaceOfResidence(this);
        return this;
    }

    public City removePersonplaceOfResidence(Profile profile) {
        this.personplaceOfResidences.remove(profile);
        profile.setPlaceOfResidence(null);
        return this;
    }

    public Set<Establishment> getEstablishments() {
        return this.establishments;
    }

    public void setEstablishments(Set<Establishment> establishments) {
        if (this.establishments != null) {
            this.establishments.forEach(i -> i.setCity(null));
        }
        if (establishments != null) {
            establishments.forEach(i -> i.setCity(this));
        }
        this.establishments = establishments;
    }

    public City establishments(Set<Establishment> establishments) {
        this.setEstablishments(establishments);
        return this;
    }

    public City addEstablishment(Establishment establishment) {
        this.establishments.add(establishment);
        establishment.setCity(this);
        return this;
    }

    public City removeEstablishment(Establishment establishment) {
        this.establishments.remove(establishment);
        establishment.setCity(null);
        return this;
    }

    public Set<EducationalInstitution> getEducationalInstitutions() {
        return this.educationalInstitutions;
    }

    public void setEducationalInstitutions(Set<EducationalInstitution> educationalInstitutions) {
        if (this.educationalInstitutions != null) {
            this.educationalInstitutions.forEach(i -> i.setCity(null));
        }
        if (educationalInstitutions != null) {
            educationalInstitutions.forEach(i -> i.setCity(this));
        }
        this.educationalInstitutions = educationalInstitutions;
    }

    public City educationalInstitutions(Set<EducationalInstitution> educationalInstitutions) {
        this.setEducationalInstitutions(educationalInstitutions);
        return this;
    }

    public City addEducationalInstitution(EducationalInstitution educationalInstitution) {
        this.educationalInstitutions.add(educationalInstitution);
        educationalInstitution.setCity(this);
        return this;
    }

    public City removeEducationalInstitution(EducationalInstitution educationalInstitution) {
        this.educationalInstitutions.remove(educationalInstitution);
        educationalInstitution.setCity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return id != null && id.equals(((City) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", governorate='" + getGovernorate() + "'" +
            ", isGovernorate='" + getIsGovernorate() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
