package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Beneficiaries;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * A Beneficiary.
 */
@Entity
@Table(name = "beneficiary")
@Inheritance(strategy = InheritanceType.JOINED)
public class Beneficiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beneficiary_reference", unique = true)
    protected String beneficiaryReference;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "beneficiary_type", nullable = false)
    protected Beneficiaries beneficiaryType;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "donationsIssued", "nature", "beneficiary", "items" }, allowSetters = true)
    private Set<DonationDetails> donationdetails = new HashSet<>();

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "beneficiary" }, allowSetters = true)
    private Set<Visit> visits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "donations", "beneficiaries", "authorizingOfficerProfile" }, allowSetters = true)
    protected AuthorizingOfficer authorizingOfficer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "beneficiaries", "tutorProfile" }, allowSetters = true)
    protected Tutor tutor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beneficiary id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeneficiaryReference() {
        return this.beneficiaryReference;
    }

    public Beneficiary beneficiaryReference(String beneficiaryReference) {
        this.setBeneficiaryReference(beneficiaryReference);
        return this;
    }

    public void setBeneficiaryReference(String beneficiaryReference) {
        this.beneficiaryReference = beneficiaryReference;
    }

    public Beneficiaries getBeneficiaryType() {
        return this.beneficiaryType;
    }

    public Beneficiary beneficiaryType(Beneficiaries beneficiaryType) {
        this.setBeneficiaryType(beneficiaryType);
        return this;
    }

    public void setBeneficiaryType(Beneficiaries beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public Beneficiary archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<DonationDetails> getDonationdetails() {
        return this.donationdetails;
    }

    public void setDonationdetails(Set<DonationDetails> donationDetails) {
        if (this.donationdetails != null) {
            this.donationdetails.forEach(i -> i.setBeneficiary(null));
        }
        if (donationDetails != null) {
            donationDetails.forEach(i -> i.setBeneficiary(this));
        }
        this.donationdetails = donationDetails;
    }

    public Beneficiary donationdetails(Set<DonationDetails> donationDetails) {
        this.setDonationdetails(donationDetails);
        return this;
    }

    public Beneficiary addDonationdetails(DonationDetails donationDetails) {
        this.donationdetails.add(donationDetails);
        donationDetails.setBeneficiary(this);
        return this;
    }

    public Beneficiary removeDonationdetails(DonationDetails donationDetails) {
        this.donationdetails.remove(donationDetails);
        donationDetails.setBeneficiary(null);
        return this;
    }

    public Set<Visit> getVisits() {
        return this.visits;
    }

    public void setVisits(Set<Visit> visits) {
        if (this.visits != null) {
            this.visits.forEach(i -> i.setBeneficiary(null));
        }
        if (visits != null) {
            visits.forEach(i -> i.setBeneficiary(this));
        }
        this.visits = visits;
    }

    public Beneficiary visits(Set<Visit> visits) {
        this.setVisits(visits);
        return this;
    }

    public Beneficiary addVisit(Visit visit) {
        this.visits.add(visit);
        visit.setBeneficiary(this);
        return this;
    }

    public Beneficiary removeVisit(Visit visit) {
        this.visits.remove(visit);
        visit.setBeneficiary(null);
        return this;
    }

    public AuthorizingOfficer getAuthorizingOfficer() {
        return this.authorizingOfficer;
    }

    public void setAuthorizingOfficer(AuthorizingOfficer authorizingOfficer) {
        this.authorizingOfficer = authorizingOfficer;
    }

    public Beneficiary authorizingOfficer(AuthorizingOfficer authorizingOfficer) {
        this.setAuthorizingOfficer(authorizingOfficer);
        return this;
    }

    public Tutor getTutor() {
        return this.tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Beneficiary tutor(Tutor tutor) {
        this.setTutor(tutor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beneficiary)) {
            return false;
        }
        return id != null && id.equals(((Beneficiary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beneficiary{" +
            "id=" + getId() +
            ", beneficiaryReference='" + getBeneficiaryReference() + "'" +
            ", beneficiaryType='" + getBeneficiaryType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
