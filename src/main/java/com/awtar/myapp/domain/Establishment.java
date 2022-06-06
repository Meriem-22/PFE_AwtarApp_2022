package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Establishment.
 */
@Entity
@Table(name = "establishment")
@PrimaryKeyJoinColumn(name = "id")
public class Establishment extends Beneficiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "activity")
    private String activity;

    @Column(name = "manager")
    private String manager;

    @Column(name = "manager_cin")
    private String managerCin;

    @Column(name = "contact")
    private String contact;

    @Column(name = "adress")
    private String adress;

    @Column(name = "cp")
    private String cp;

    @Column(name = "region")
    private String region;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "site")
    private String site;

    @Column(name = "remark")
    private String remark;

    @OneToMany(mappedBy = "establishment")
    @JsonIgnoreProperties(value = { "establishment" }, allowSetters = true)
    private Set<Report> reports = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "establishments" }, allowSetters = true)
    private EstablishmentType establishmentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "personbirthPlaces", "personplaceOfResidences", "establishments", "educationalInstitutions" },
        allowSetters = true
    )
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return super.getId();
    }

    public Establishment id(Long id) {
        super.setId(id);
        return this;
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public String getName() {
        return this.name;
    }

    public Establishment name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return this.activity;
    }

    public Establishment activity(String activity) {
        this.setActivity(activity);
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getManager() {
        return this.manager;
    }

    public Establishment manager(String manager) {
        this.setManager(manager);
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerCin() {
        return this.managerCin;
    }

    public Establishment managerCin(String managerCin) {
        this.setManagerCin(managerCin);
        return this;
    }

    public void setManagerCin(String managerCin) {
        this.managerCin = managerCin;
    }

    public String getContact() {
        return this.contact;
    }

    public Establishment contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdress() {
        return this.adress;
    }

    public Establishment adress(String adress) {
        this.setAdress(adress);
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCp() {
        return this.cp;
    }

    public Establishment cp(String cp) {
        this.setCp(cp);
        return this;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getRegion() {
        return this.region;
    }

    public Establishment region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return this.phone;
    }

    public Establishment phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return this.fax;
    }

    public Establishment fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public Establishment email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return this.site;
    }

    public Establishment site(String site) {
        this.setSite(site);
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRemark() {
        return this.remark;
    }

    public Establishment remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<Report> getReports() {
        return this.reports;
    }

    public void setReports(Set<Report> reports) {
        if (this.reports != null) {
            this.reports.forEach(i -> i.setEstablishment(null));
        }
        if (reports != null) {
            reports.forEach(i -> i.setEstablishment(this));
        }
        this.reports = reports;
    }

    public Establishment reports(Set<Report> reports) {
        this.setReports(reports);
        return this;
    }

    public Establishment addReport(Report report) {
        this.reports.add(report);
        report.setEstablishment(this);
        return this;
    }

    public Establishment removeReport(Report report) {
        this.reports.remove(report);
        report.setEstablishment(null);
        return this;
    }

    public EstablishmentType getEstablishmentType() {
        return this.establishmentType;
    }

    public void setEstablishmentType(EstablishmentType establishmentType) {
        this.establishmentType = establishmentType;
    }

    public Establishment establishmentType(EstablishmentType establishmentType) {
        this.setEstablishmentType(establishmentType);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Establishment city(City city) {
        this.setCity(city);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Establishment)) {
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
        return "Establishment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activity='" + getActivity() + "'" +
            ", manager='" + getManager() + "'" +
            ", managerCin='" + getManagerCin() + "'" +
            ", contact='" + getContact() + "'" +
            ", adress='" + getAdress() + "'" +
            ", cp='" + getCp() + "'" +
            ", region='" + getRegion() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", site='" + getSite() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
