package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Establishment} entity.
 */
public class EstablishmentDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String activity;

    private String manager;

    private String managerCin;

    private String contact;

    private String adress;

    private String cp;

    private String region;

    private String phone;

    private String fax;

    private String email;

    private String site;

    private String remark;

    private EstablishmentTypeDTO establishmentType;

    private CityDTO city;

    private ProfileDTO authorizingOfficer;

    private ProfileDTO tutor;

    private Long NbInCity;

    public EstablishmentDTO() {}

    public EstablishmentDTO(Long NbInCity) {
        this.NbInCity = NbInCity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerCin() {
        return managerCin;
    }

    public void setManagerCin(String managerCin) {
        this.managerCin = managerCin;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public EstablishmentTypeDTO getEstablishmentType() {
        return establishmentType;
    }

    public void setEstablishmentType(EstablishmentTypeDTO establishmentType) {
        this.establishmentType = establishmentType;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public ProfileDTO getAuthorizingOfficer() {
        return authorizingOfficer;
    }

    public void setAuthorizingOfficer(ProfileDTO authorizingOfficer) {
        this.authorizingOfficer = authorizingOfficer;
    }

    public ProfileDTO getTutor() {
        return tutor;
    }

    public void setTutor(ProfileDTO tutor) {
        this.tutor = tutor;
    }

    public Long getNbInCity() {
        return NbInCity;
    }

    public void setNbInCity(Long nbInCity) {
        NbInCity = nbInCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstablishmentDTO)) {
            return false;
        }

        EstablishmentDTO establishmentDTO = (EstablishmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, establishmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstablishmentDTO{" +
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
            ", establishmentType=" + getEstablishmentType() +
            ", city=" + getCity() +
            "}";
    }
}
