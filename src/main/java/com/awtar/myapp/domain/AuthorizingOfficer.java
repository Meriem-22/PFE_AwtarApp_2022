package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A AuthorizingOfficer.
 */
@Entity
@Table(name = "authorizing_officer")
public class AuthorizingOfficer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "abbreviation", nullable = false, unique = true)
    private String abbreviation;

    @Column(name = "activity")
    private String activity;

    @Column(name = "manager")
    private String manager;

    @Column(name = "manager_cin")
    private String managerCin;

    @OneToMany(mappedBy = "authorizingOfficer")
    @JsonIgnoreProperties(value = { "authorizingOfficer", "items" }, allowSetters = true)
    private Set<DonationsReceived> donations = new HashSet<>();

    @OneToMany(mappedBy = "authorizingOfficer")
    @JsonIgnoreProperties(value = { "donationdetails", "visits", "authorizingOfficer", "tutor" }, allowSetters = true)
    private Set<Beneficiary> beneficiaries = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "parent", "child", "authorizingOfficer", "tutor", "statusOfHealths", "birthPlace", "placeOfResidence" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "authorizingOfficer")
    private Profile authorizingOfficerProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuthorizingOfficer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public AuthorizingOfficer abbreviation(String abbreviation) {
        this.setAbbreviation(abbreviation);
        return this;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getActivity() {
        return this.activity;
    }

    public AuthorizingOfficer activity(String activity) {
        this.setActivity(activity);
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getManager() {
        return this.manager;
    }

    public AuthorizingOfficer manager(String manager) {
        this.setManager(manager);
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerCin() {
        return this.managerCin;
    }

    public AuthorizingOfficer managerCin(String managerCin) {
        this.setManagerCin(managerCin);
        return this;
    }

    public void setManagerCin(String managerCin) {
        this.managerCin = managerCin;
    }

    public Set<DonationsReceived> getDonations() {
        return this.donations;
    }

    public void setDonations(Set<DonationsReceived> donationsReceiveds) {
        if (this.donations != null) {
            this.donations.forEach(i -> i.setAuthorizingOfficer(null));
        }
        if (donationsReceiveds != null) {
            donationsReceiveds.forEach(i -> i.setAuthorizingOfficer(this));
        }
        this.donations = donationsReceiveds;
    }

    public AuthorizingOfficer donations(Set<DonationsReceived> donationsReceiveds) {
        this.setDonations(donationsReceiveds);
        return this;
    }

    public AuthorizingOfficer addDonations(DonationsReceived donationsReceived) {
        this.donations.add(donationsReceived);
        donationsReceived.setAuthorizingOfficer(this);
        return this;
    }

    public AuthorizingOfficer removeDonations(DonationsReceived donationsReceived) {
        this.donations.remove(donationsReceived);
        donationsReceived.setAuthorizingOfficer(null);
        return this;
    }

    public Set<Beneficiary> getBeneficiaries() {
        return this.beneficiaries;
    }

    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
        if (this.beneficiaries != null) {
            this.beneficiaries.forEach(i -> i.setAuthorizingOfficer(null));
        }
        if (beneficiaries != null) {
            beneficiaries.forEach(i -> i.setAuthorizingOfficer(this));
        }
        this.beneficiaries = beneficiaries;
    }

    public AuthorizingOfficer beneficiaries(Set<Beneficiary> beneficiaries) {
        this.setBeneficiaries(beneficiaries);
        return this;
    }

    public AuthorizingOfficer addBeneficiary(Beneficiary beneficiary) {
        this.beneficiaries.add(beneficiary);
        beneficiary.setAuthorizingOfficer(this);
        return this;
    }

    public AuthorizingOfficer removeBeneficiary(Beneficiary beneficiary) {
        this.beneficiaries.remove(beneficiary);
        beneficiary.setAuthorizingOfficer(null);
        return this;
    }

    public Profile getAuthorizingOfficerProfile() {
        return this.authorizingOfficerProfile;
    }

    public void setAuthorizingOfficerProfile(Profile profile) {
        if (this.authorizingOfficerProfile != null) {
            this.authorizingOfficerProfile.setAuthorizingOfficer(null);
        }
        if (profile != null) {
            profile.setAuthorizingOfficer(this);
        }
        this.authorizingOfficerProfile = profile;
    }

    public AuthorizingOfficer authorizingOfficerProfile(Profile profile) {
        this.setAuthorizingOfficerProfile(profile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorizingOfficer)) {
            return false;
        }
        return id != null && id.equals(((AuthorizingOfficer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthorizingOfficer{" +
            "id=" + getId() +
            ", abbreviation='" + getAbbreviation() + "'" +
            ", activity='" + getActivity() + "'" +
            ", manager='" + getManager() + "'" +
            ", managerCin='" + getManagerCin() + "'" +
            "}";
    }
}
