package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Child.
 */
@Entity
@Table(name = "child")
@PrimaryKeyJoinColumn(name = "id")
public class Child extends Beneficiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "schoolLevel", "child", "educationalInstitution" }, allowSetters = true)
    private Set<TeachingCurriculum> teachingCurricula = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "parent", "child", "authorizingOfficer", "tutor", "statusOfHealths", "birthPlace", "placeOfResidence" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "child")
    private Profile childProfile;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parents", "children", "head" }, allowSetters = true)
    private Family family;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return super.getId();
    }

    public Child id(Long id) {
        super.setId(id);
        return this;
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public Set<TeachingCurriculum> getTeachingCurricula() {
        return this.teachingCurricula;
    }

    public void setTeachingCurricula(Set<TeachingCurriculum> teachingCurricula) {
        if (this.teachingCurricula != null) {
            this.teachingCurricula.forEach(i -> i.setChild(null));
        }
        if (teachingCurricula != null) {
            teachingCurricula.forEach(i -> i.setChild(this));
        }
        this.teachingCurricula = teachingCurricula;
    }

    public Child teachingCurricula(Set<TeachingCurriculum> teachingCurricula) {
        this.setTeachingCurricula(teachingCurricula);
        return this;
    }

    public Child addTeachingCurriculum(TeachingCurriculum teachingCurriculum) {
        this.teachingCurricula.add(teachingCurriculum);
        teachingCurriculum.setChild(this);
        return this;
    }

    public Child removeTeachingCurriculum(TeachingCurriculum teachingCurriculum) {
        this.teachingCurricula.remove(teachingCurriculum);
        teachingCurriculum.setChild(null);
        return this;
    }

    public Profile getChildProfile() {
        return this.childProfile;
    }

    public void setChildProfile(Profile profile) {
        if (this.childProfile != null) {
            this.childProfile.setChild(null);
        }
        if (profile != null) {
            profile.setChild(this);
        }
        this.childProfile = profile;
    }

    public Child childProfile(Profile profile) {
        this.setChildProfile(profile);
        return this;
    }

    public Family getFamily() {
        return this.family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Child family(Family family) {
        this.setFamily(family);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Child)) {
            return false;
        }
        return getId() != null && getId().equals(((Child) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Child{" +
            "id=" + getId() +
            "}";
    }
}
