package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DonationsReceived.
 */
@Entity
@Table(name = "donations_received")
public class DonationsReceived implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "url_photo")
    private byte[] urlPhoto;

    @Column(name = "url_photo_content_type")
    private String urlPhotoContentType;

    @Column(name = "archivated")
    private Boolean archivated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "donations", "beneficiaries", "authorizingOfficerProfile" }, allowSetters = true)
    private AuthorizingOfficer authorizingOfficer;

    @OneToMany(mappedBy = "donationsReceived")
    @JsonIgnoreProperties(value = { "item", "donationsReceived" }, allowSetters = true)
    private Set<DonationsReceivedItem> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DonationsReceived id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getUrlPhoto() {
        return this.urlPhoto;
    }

    public DonationsReceived urlPhoto(byte[] urlPhoto) {
        this.setUrlPhoto(urlPhoto);
        return this;
    }

    public void setUrlPhoto(byte[] urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlPhotoContentType() {
        return this.urlPhotoContentType;
    }

    public DonationsReceived urlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
        return this;
    }

    public void setUrlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public DonationsReceived archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public AuthorizingOfficer getAuthorizingOfficer() {
        return this.authorizingOfficer;
    }

    public void setAuthorizingOfficer(AuthorizingOfficer authorizingOfficer) {
        this.authorizingOfficer = authorizingOfficer;
    }

    public DonationsReceived authorizingOfficer(AuthorizingOfficer authorizingOfficer) {
        this.setAuthorizingOfficer(authorizingOfficer);
        return this;
    }

    public Set<DonationsReceivedItem> getItems() {
        return this.items;
    }

    public void setItems(Set<DonationsReceivedItem> donationsReceivedItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setDonationsReceived(null));
        }
        if (donationsReceivedItems != null) {
            donationsReceivedItems.forEach(i -> i.setDonationsReceived(this));
        }
        this.items = donationsReceivedItems;
    }

    public DonationsReceived items(Set<DonationsReceivedItem> donationsReceivedItems) {
        this.setItems(donationsReceivedItems);
        return this;
    }

    public DonationsReceived addItem(DonationsReceivedItem donationsReceivedItem) {
        this.items.add(donationsReceivedItem);
        donationsReceivedItem.setDonationsReceived(this);
        return this;
    }

    public DonationsReceived removeItem(DonationsReceivedItem donationsReceivedItem) {
        this.items.remove(donationsReceivedItem);
        donationsReceivedItem.setDonationsReceived(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationsReceived)) {
            return false;
        }
        return id != null && id.equals(((DonationsReceived) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationsReceived{" +
            "id=" + getId() +
            ", urlPhoto='" + getUrlPhoto() + "'" +
            ", urlPhotoContentType='" + getUrlPhotoContentType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
