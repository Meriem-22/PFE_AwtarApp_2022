package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.ItemGender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "url_photo")
    private byte[] urlPhoto;

    @Column(name = "url_photo_content_type")
    private String urlPhotoContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private ItemGender gender;

    @Column(name = "composed")
    private Boolean composed;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "item")
    @JsonIgnoreProperties(value = { "item" }, allowSetters = true)
    private Set<ItemValue> itemValues = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "items", "donationdetails" }, allowSetters = true)
    private Nature nature;

    @OneToMany(mappedBy = "item")
    @JsonIgnoreProperties(value = { "donationDetails", "item" }, allowSetters = true)
    private Set<DonationItemDetails> donationDetails = new HashSet<>();

    @OneToMany(mappedBy = "composant")
    @JsonIgnoreProperties(value = { "composant", "composer" }, allowSetters = true)
    private Set<CompositeItem> composers = new HashSet<>();

    @OneToMany(mappedBy = "composer")
    @JsonIgnoreProperties(value = { "composant", "composer" }, allowSetters = true)
    private Set<CompositeItem> composants = new HashSet<>();

    @OneToMany(mappedBy = "item")
    @JsonIgnoreProperties(value = { "item", "donationsReceived" }, allowSetters = true)
    private Set<DonationsReceivedItem> donationsReceiveds = new HashSet<>();

    @OneToMany(mappedBy = "item")
    @JsonIgnoreProperties(value = { "item", "childStatus" }, allowSetters = true)
    private Set<ChildStatusItem> childStatuses = new HashSet<>();

    @OneToMany(mappedBy = "item")
    @JsonIgnoreProperties(value = { "item", "schoolLevel" }, allowSetters = true)
    private Set<SchoolLevelItem> schoolLevels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Item id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Item name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getUrlPhoto() {
        return this.urlPhoto;
    }

    public Item urlPhoto(byte[] urlPhoto) {
        this.setUrlPhoto(urlPhoto);
        return this;
    }

    public void setUrlPhoto(byte[] urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlPhotoContentType() {
        return this.urlPhotoContentType;
    }

    public Item urlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
        return this;
    }

    public void setUrlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public ItemGender getGender() {
        return this.gender;
    }

    public Item gender(ItemGender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(ItemGender gender) {
        this.gender = gender;
    }

    public Boolean getComposed() {
        return this.composed;
    }

    public Item composed(Boolean composed) {
        this.setComposed(composed);
        return this;
    }

    public void setComposed(Boolean composed) {
        this.composed = composed;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public Item archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<ItemValue> getItemValues() {
        return this.itemValues;
    }

    public void setItemValues(Set<ItemValue> itemValues) {
        if (this.itemValues != null) {
            this.itemValues.forEach(i -> i.setItem(null));
        }
        if (itemValues != null) {
            itemValues.forEach(i -> i.setItem(this));
        }
        this.itemValues = itemValues;
    }

    public Item itemValues(Set<ItemValue> itemValues) {
        this.setItemValues(itemValues);
        return this;
    }

    public Item addItemValue(ItemValue itemValue) {
        this.itemValues.add(itemValue);
        itemValue.setItem(this);
        return this;
    }

    public Item removeItemValue(ItemValue itemValue) {
        this.itemValues.remove(itemValue);
        itemValue.setItem(null);
        return this;
    }

    public Nature getNature() {
        return this.nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public Item nature(Nature nature) {
        this.setNature(nature);
        return this;
    }

    public Set<DonationItemDetails> getDonationDetails() {
        return this.donationDetails;
    }

    public void setDonationDetails(Set<DonationItemDetails> donationItemDetails) {
        if (this.donationDetails != null) {
            this.donationDetails.forEach(i -> i.setItem(null));
        }
        if (donationItemDetails != null) {
            donationItemDetails.forEach(i -> i.setItem(this));
        }
        this.donationDetails = donationItemDetails;
    }

    public Item donationDetails(Set<DonationItemDetails> donationItemDetails) {
        this.setDonationDetails(donationItemDetails);
        return this;
    }

    public Item addDonationDetails(DonationItemDetails donationItemDetails) {
        this.donationDetails.add(donationItemDetails);
        donationItemDetails.setItem(this);
        return this;
    }

    public Item removeDonationDetails(DonationItemDetails donationItemDetails) {
        this.donationDetails.remove(donationItemDetails);
        donationItemDetails.setItem(null);
        return this;
    }

    public Set<CompositeItem> getComposers() {
        return this.composers;
    }

    public void setComposers(Set<CompositeItem> compositeItems) {
        if (this.composers != null) {
            this.composers.forEach(i -> i.setComposant(null));
        }
        if (compositeItems != null) {
            compositeItems.forEach(i -> i.setComposant(this));
        }
        this.composers = compositeItems;
    }

    public Item composers(Set<CompositeItem> compositeItems) {
        this.setComposers(compositeItems);
        return this;
    }

    public Item addComposer(CompositeItem compositeItem) {
        this.composers.add(compositeItem);
        compositeItem.setComposant(this);
        return this;
    }

    public Item removeComposer(CompositeItem compositeItem) {
        this.composers.remove(compositeItem);
        compositeItem.setComposant(null);
        return this;
    }

    public Set<CompositeItem> getComposants() {
        return this.composants;
    }

    public void setComposants(Set<CompositeItem> compositeItems) {
        if (this.composants != null) {
            this.composants.forEach(i -> i.setComposer(null));
        }
        if (compositeItems != null) {
            compositeItems.forEach(i -> i.setComposer(this));
        }
        this.composants = compositeItems;
    }

    public Item composants(Set<CompositeItem> compositeItems) {
        this.setComposants(compositeItems);
        return this;
    }

    public Item addComposant(CompositeItem compositeItem) {
        this.composants.add(compositeItem);
        compositeItem.setComposer(this);
        return this;
    }

    public Item removeComposant(CompositeItem compositeItem) {
        this.composants.remove(compositeItem);
        compositeItem.setComposer(null);
        return this;
    }

    public Set<DonationsReceivedItem> getDonationsReceiveds() {
        return this.donationsReceiveds;
    }

    public void setDonationsReceiveds(Set<DonationsReceivedItem> donationsReceivedItems) {
        if (this.donationsReceiveds != null) {
            this.donationsReceiveds.forEach(i -> i.setItem(null));
        }
        if (donationsReceivedItems != null) {
            donationsReceivedItems.forEach(i -> i.setItem(this));
        }
        this.donationsReceiveds = donationsReceivedItems;
    }

    public Item donationsReceiveds(Set<DonationsReceivedItem> donationsReceivedItems) {
        this.setDonationsReceiveds(donationsReceivedItems);
        return this;
    }

    public Item addDonationsReceived(DonationsReceivedItem donationsReceivedItem) {
        this.donationsReceiveds.add(donationsReceivedItem);
        donationsReceivedItem.setItem(this);
        return this;
    }

    public Item removeDonationsReceived(DonationsReceivedItem donationsReceivedItem) {
        this.donationsReceiveds.remove(donationsReceivedItem);
        donationsReceivedItem.setItem(null);
        return this;
    }

    public Set<ChildStatusItem> getChildStatuses() {
        return this.childStatuses;
    }

    public void setChildStatuses(Set<ChildStatusItem> childStatusItems) {
        if (this.childStatuses != null) {
            this.childStatuses.forEach(i -> i.setItem(null));
        }
        if (childStatusItems != null) {
            childStatusItems.forEach(i -> i.setItem(this));
        }
        this.childStatuses = childStatusItems;
    }

    public Item childStatuses(Set<ChildStatusItem> childStatusItems) {
        this.setChildStatuses(childStatusItems);
        return this;
    }

    public Item addChildStatus(ChildStatusItem childStatusItem) {
        this.childStatuses.add(childStatusItem);
        childStatusItem.setItem(this);
        return this;
    }

    public Item removeChildStatus(ChildStatusItem childStatusItem) {
        this.childStatuses.remove(childStatusItem);
        childStatusItem.setItem(null);
        return this;
    }

    public Set<SchoolLevelItem> getSchoolLevels() {
        return this.schoolLevels;
    }

    public void setSchoolLevels(Set<SchoolLevelItem> schoolLevelItems) {
        if (this.schoolLevels != null) {
            this.schoolLevels.forEach(i -> i.setItem(null));
        }
        if (schoolLevelItems != null) {
            schoolLevelItems.forEach(i -> i.setItem(this));
        }
        this.schoolLevels = schoolLevelItems;
    }

    public Item schoolLevels(Set<SchoolLevelItem> schoolLevelItems) {
        this.setSchoolLevels(schoolLevelItems);
        return this;
    }

    public Item addSchoolLevel(SchoolLevelItem schoolLevelItem) {
        this.schoolLevels.add(schoolLevelItem);
        schoolLevelItem.setItem(this);
        return this;
    }

    public Item removeSchoolLevel(SchoolLevelItem schoolLevelItem) {
        this.schoolLevels.remove(schoolLevelItem);
        schoolLevelItem.setItem(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", urlPhoto='" + getUrlPhoto() + "'" +
            ", urlPhotoContentType='" + getUrlPhotoContentType() + "'" +
            ", gender='" + getGender() + "'" +
            ", composed='" + getComposed() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
