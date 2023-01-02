package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.DonationItemDetails;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.repository.BeneficiaryRepository;
import com.awtar.myapp.repository.ChildRepository;
import com.awtar.myapp.repository.DonationDetailsRepository;
import com.awtar.myapp.repository.DonationItemDetailsRepository;
import com.awtar.myapp.repository.DonationsIssuedRepository;
import com.awtar.myapp.repository.EstablishmentRepository;
import com.awtar.myapp.repository.FamilyRepository;
import com.awtar.myapp.repository.ItemRepository;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.service.DonationsIssuedService;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import com.awtar.myapp.service.mapper.DonationDetailsMapper;
import com.awtar.myapp.service.mapper.DonationItemDetailsMapper;
import com.awtar.myapp.service.mapper.DonationsIssuedMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DonationsIssued}.
 */
@Service
@Transactional
public class DonationsIssuedServiceImpl implements DonationsIssuedService {

    private final Logger log = LoggerFactory.getLogger(DonationsIssuedServiceImpl.class);

    private final DonationsIssuedRepository donationsIssuedRepository;

    private final DonationsIssuedMapper donationsIssuedMapper;

    private final DonationDetailsMapper donationDetailsMapper;

    private final DonationDetailsRepository donationDetailsRepository;

    private final DonationItemDetailsMapper donationItemDetailsMapper;

    private final DonationItemDetailsRepository donationItemDetailsRepository;

    private final FamilyRepository familyRepository;

    private final EstablishmentRepository establishmentRepository;

    private final BeneficiaryRepository beneficiaryRepository;

    private final ProfileRepository profileRepository;

    private final ItemRepository itemRepository;

    public DonationsIssuedServiceImpl(
        DonationsIssuedRepository donationsIssuedRepository,
        DonationsIssuedMapper donationsIssuedMapper,
        DonationDetailsMapper donationDetailsMapper,
        DonationDetailsRepository donationDetailsRepository,
        DonationItemDetailsMapper donationItemDetailsMapper,
        DonationItemDetailsRepository donationItemDetailsRepository,
        FamilyRepository familyRepository,
        EstablishmentRepository establishmentRepository,
        ProfileRepository profileRepository,
        BeneficiaryRepository beneficiaryRepository,
        ItemRepository itemRepository
    ) {
        this.donationsIssuedRepository = donationsIssuedRepository;
        this.donationsIssuedMapper = donationsIssuedMapper;
        this.donationDetailsMapper = donationDetailsMapper;
        this.donationDetailsRepository = donationDetailsRepository;
        this.donationItemDetailsMapper = donationItemDetailsMapper;
        this.donationItemDetailsRepository = donationItemDetailsRepository;
        this.familyRepository = familyRepository;
        this.establishmentRepository = establishmentRepository;
        this.profileRepository = profileRepository;
        this.beneficiaryRepository = beneficiaryRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public DonationsIssuedDTO save(DonationsIssuedDTO donationsIssuedDTO) {
        log.debug("Request to save DonationsIssued : {}", donationsIssuedDTO);
        DonationsIssued donationsIssued = donationsIssuedMapper.toEntity(donationsIssuedDTO);
        donationsIssued = donationsIssuedRepository.save(donationsIssued);
        return donationsIssuedMapper.toDto(donationsIssued);
    }

    @Override
    public DonationsIssuedDTO update(DonationsIssuedDTO donationsIssuedDTO) {
        log.debug("Request to save DonationsIssued : {}", donationsIssuedDTO);
        DonationsIssued donationsIssued = donationsIssuedMapper.toEntity(donationsIssuedDTO);
        donationsIssued = donationsIssuedRepository.save(donationsIssued);
        return donationsIssuedMapper.toDto(donationsIssued);
    }

    @Override
    public Optional<DonationsIssuedDTO> partialUpdate(DonationsIssuedDTO donationsIssuedDTO) {
        log.debug("Request to partially update DonationsIssued : {}", donationsIssuedDTO);

        return donationsIssuedRepository
            .findById(donationsIssuedDTO.getId())
            .map(existingDonationsIssued -> {
                donationsIssuedMapper.partialUpdate(existingDonationsIssued, donationsIssuedDTO);

                return existingDonationsIssued;
            })
            .map(donationsIssuedRepository::save)
            .map(donationsIssuedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonationsIssuedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonationsIssueds");
        return donationsIssuedRepository.findAll(pageable).map(donationsIssuedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonationsIssuedDTO> findOne(Long id) {
        log.debug("Request to get DonationsIssued : {}", id);
        return donationsIssuedRepository.findById(id).map(donationsIssuedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DonationsIssued : {}", id);
        donationsIssuedRepository.deleteById(id);
    }

    @Override
    public DonationsIssuedDTO saveCompletedDonationsIssued(DonationsIssuedDTO donationsIssuedDTO) {
        log.debug("Request to save DonationsIssued : {}", donationsIssuedDTO);
        DonationsIssued donationsIssued = donationsIssuedMapper.toEntity(donationsIssuedDTO);
        donationsIssued = donationsIssuedRepository.save(donationsIssued);
        LocalDate localDate = LocalDate.now();
        DonationDetailsDTO donationDetails[];
        donationDetails = donationsIssuedDTO.getDonationsDetailsN();

        for (int i = 0; i < donationDetails.length; i++) {
            DonationItemDetailsDTO donationItemDetails = donationDetails[i].getDonationItemDetails();
            Long[] beneficiarys = donationDetails[i].getIdsBeneficiary();
            Long[] itemsWithQuantitys = donationItemDetails.getItemsWithQuantitys();
            Integer[] quantitys = donationItemDetails.getQuantityOfItems();
            Long[] itemsWithoutQuantitys = donationItemDetails.getItemsWithoutQuantitys();

            for (int b = 0; b < beneficiarys.length; b++) {
                DonationDetails donationDetail = new DonationDetails();
                donationDetail = donationDetailsMapper.toEntity(donationDetails[i]);
                donationDetail.setDonationsIssued(donationsIssued);
                donationDetail.setBeneficiary(beneficiaryRepository.getById(beneficiarys[b]));
                donationDetail = donationDetailsRepository.save(donationDetail);

                for (int j = 0; j < itemsWithQuantitys.length; j++) {
                    DonationItemDetails donationItemDetail1 = new DonationItemDetails();
                    donationItemDetail1.archivated(false);
                    donationItemDetail1.setDate(localDate);
                    donationItemDetail1.setItem(itemRepository.getById(itemsWithQuantitys[j]));
                    donationItemDetail1.setQuantity(quantitys[j]);
                    donationItemDetail1.setDonationDetails(donationDetail);
                    donationItemDetail1 = donationItemDetailsRepository.save(donationItemDetail1);
                }

                for (int k = 0; k < itemsWithoutQuantitys.length; k++) {
                    DonationItemDetails donationItemDetail2 = new DonationItemDetails();
                    donationItemDetail2.archivated(false);
                    donationItemDetail2.setDate(localDate);
                    donationItemDetail2.setItem(itemRepository.getById(itemsWithoutQuantitys[k]));
                    donationItemDetail2.setQuantity(1);
                    donationItemDetail2.setDonationDetails(donationDetail);
                    donationItemDetail2 = donationItemDetailsRepository.save(donationItemDetail2);
                }
            }
        }

        return donationsIssuedMapper.toDto(donationsIssued);
    }

    @Override
    public List<DonationsIssuedDTO> getLastValidatedDonations() {
        List<DonationsIssued> donations = donationsIssuedRepository.findAllDetailsItemDonations();
        return donationsIssuedMapper.toDto(donations);
    }

    @Override
    public List<DonationsIssuedDTO> Upcomingscheduleddonations() {
        LocalDate date = LocalDate.now();
        List<DonationsIssued> donations = donationsIssuedRepository.Upcomingscheduleddonations(date);
        return donationsIssuedMapper.toDto(donations);
    }

    @Override
    public List<DonationsIssuedDTO> UpcomingDonationsValidated() {
        LocalDate date = LocalDate.now();
        List<DonationsIssued> donations = donationsIssuedRepository.UpcomingDonationsValidated(date);
        return donationsIssuedMapper.toDto(donations);
    }

    @Override
    public List<DonationsIssuedDTO> IssuedDonationsOfCurrentYearByMonth() {
        List<DonationsIssuedDTO> donations = donationsIssuedRepository.findIssuedDonationsByMonth();
        return donations;
    }

    @Override
    public List<DonationsIssuedDTO> CanceledDonationsOfCurrentYearByMonth() {
        List<DonationsIssuedDTO> donations = donationsIssuedRepository.findCanceledDonationsByMonth();
        return donations;
    }

    @Override
    public List<DonationsIssuedDTO> GetAll() {
        List<DonationsIssued> donations = donationsIssuedRepository.findAllNotArchivated();

        return donationsIssuedMapper.toDto(donations);
    }
}
