package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Tutor.
 */
@Entity
@Table(name = "tutor")
public class Tutor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "activity")
    private String activity;

    @Column(name = "manager")
    private String manager;

    @Column(name = "manager_cin")
    private String managerCin;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "donationdetails", "visits", "authorizingOfficer", "tutor" }, allowSetters = true)
    private Set<Beneficiary> beneficiaries = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "parent", "child", "authorizingOfficer", "tutor", "statusOfHealths", "birthPlace", "placeOfResidence" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "tutor")
    private Profile tutorProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tutor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return this.activity;
    }

    public Tutor activity(String activity) {
        this.setActivity(activity);
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getManager() {
        return this.manager;
    }

    public Tutor manager(String manager) {
        this.setManager(manager);
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerCin() {
        return this.managerCin;
    }

    public Tutor managerCin(String managerCin) {
        this.setManagerCin(managerCin);
        return this;
    }

    public void setManagerCin(String managerCin) {
        this.managerCin = managerCin;
    }

    public Set<Beneficiary> getBeneficiaries() {
        return this.beneficiaries;
    }

    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
        if (this.beneficiaries != null) {
            this.beneficiaries.forEach(i -> i.setTutor(null));
        }
        if (beneficiaries != null) {
            beneficiaries.forEach(i -> i.setTutor(this));
        }
        this.beneficiaries = beneficiaries;
    }

    public Tutor beneficiaries(Set<Beneficiary> beneficiaries) {
        this.setBeneficiaries(beneficiaries);
        return this;
    }

    public Tutor addBeneficiary(Beneficiary beneficiary) {
        this.beneficiaries.add(beneficiary);
        beneficiary.setTutor(this);
        return this;
    }

    public Tutor removeBeneficiary(Beneficiary beneficiary) {
        this.beneficiaries.remove(beneficiary);
        beneficiary.setTutor(null);
        return this;
    }

    public Profile getTutorProfile() {
        return this.tutorProfile;
    }

    public void setTutorProfile(Profile profile) {
        if (this.tutorProfile != null) {
            this.tutorProfile.setTutor(null);
        }
        if (profile != null) {
            profile.setTutor(this);
        }
        this.tutorProfile = profile;
    }

    public Tutor tutorProfile(Profile profile) {
        this.setTutorProfile(profile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tutor)) {
            return false;
        }
        return id != null && id.equals(((Tutor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tutor{" +
            "id=" + getId() +
            ", activity='" + getActivity() + "'" +
            ", manager='" + getManager() + "'" +
            ", managerCin='" + getManagerCin() + "'" +
            "}";
    }
}
