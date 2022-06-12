package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Beneficiaries;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Family.
 */
@Entity
@Table(name = "family")
@PrimaryKeyJoinColumn(name = "id")
public class Family extends Beneficiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "family_name", nullable = false, unique = true)
    private String familyName;

    @NotNull
    @Column(name = "dwelling", nullable = false)
    private String dwelling;

    @Column(name = "area")
    private String area;

    @Column(name = "notebook_of_poverty")
    private Boolean notebookOfPoverty;

    @Column(name = "notebook_of_handicap")
    private Boolean notebookOfHandicap;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "family")
    @JsonIgnoreProperties(value = { "familyHead", "parentProfile", "family" }, allowSetters = true)
    private Set<Parent> parents = new HashSet<>();

    @OneToMany(mappedBy = "family")
    @JsonIgnoreProperties(value = { "teachingCurricula", "childProfile", "family" }, allowSetters = true)
    private Set<Child> children = new HashSet<>();

    @JsonIgnoreProperties(value = { "familyHead", "parentProfile", "family" }, allowSetters = true)
    @OneToOne(mappedBy = "familyHead", cascade = CascadeType.ALL)
    private Parent head;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return super.getId();
    }

    public Family id(Long id) {
        super.setId(id);
        return this;
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public Family familyName(String familyName) {
        this.setFamilyName(familyName);
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDwelling() {
        return this.dwelling;
    }

    public Family dwelling(String dwelling) {
        this.setDwelling(dwelling);
        return this;
    }

    public void setDwelling(String dwelling) {
        this.dwelling = dwelling;
    }

    public String getArea() {
        return this.area;
    }

    public Family area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Boolean getNotebookOfPoverty() {
        return this.notebookOfPoverty;
    }

    public Family notebookOfPoverty(Boolean notebookOfPoverty) {
        this.setNotebookOfPoverty(notebookOfPoverty);
        return this;
    }

    public void setNotebookOfPoverty(Boolean notebookOfPoverty) {
        this.notebookOfPoverty = notebookOfPoverty;
    }

    public Boolean getNotebookOfHandicap() {
        return this.notebookOfHandicap;
    }

    public Family notebookOfHandicap(Boolean notebookOfHandicap) {
        this.setNotebookOfHandicap(notebookOfHandicap);
        return this;
    }

    public void setNotebookOfHandicap(Boolean notebookOfHandicap) {
        this.notebookOfHandicap = notebookOfHandicap;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public Family archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<Parent> getParents() {
        return this.parents;
    }

    public void setParents(Set<Parent> parents) {
        if (this.parents != null) {
            this.parents.forEach(i -> i.setFamily(null));
        }
        if (parents != null) {
            parents.forEach(i -> i.setFamily(this));
        }
        this.parents = parents;
    }

    public Family parents(Set<Parent> parents) {
        this.setParents(parents);
        return this;
    }

    public Family addParent(Parent parent) {
        this.parents.add(parent);
        parent.setFamily(this);
        return this;
    }

    public Family removeParent(Parent parent) {
        this.parents.remove(parent);
        parent.setFamily(null);
        return this;
    }

    public Set<Child> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Child> children) {
        if (this.children != null) {
            this.children.forEach(i -> i.setFamily(null));
        }
        if (children != null) {
            children.forEach(i -> i.setFamily(this));
        }
        this.children = children;
    }

    public Family children(Set<Child> children) {
        this.setChildren(children);
        return this;
    }

    public Family addChild(Child child) {
        this.children.add(child);
        child.setFamily(this);
        return this;
    }

    public Family removeChild(Child child) {
        this.children.remove(child);
        child.setFamily(null);
        return this;
    }

    public Parent getHead() {
        return this.head;
    }

    public void setHead(Parent parent) {
        if (this.head != null) {
            this.head.setFamilyHead(null);
        }
        if (parent != null) {
            parent.setFamilyHead(this);
        }
        this.head = parent;
    }

    public Family head(Parent parent) {
        this.setHead(parent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Family)) {
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
        return "Family{" +
            "id=" + getId() +
            ", familyName='" + getFamilyName() + "'" +
            ", dwelling='" + getDwelling() + "'" +
            ", area='" + getArea() + "'" +
            ", notebookOfPoverty='" + getNotebookOfPoverty() + "'" +
            ", notebookOfHandicap='" + getNotebookOfHandicap() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
