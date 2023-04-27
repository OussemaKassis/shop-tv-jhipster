package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.Company;
import tn.softtodo.shoptv.repository.CompanyRepository;
import tn.softtodo.shoptv.service.CompanyService;

/**
 * Service Implementation for managing {@link Company}.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        log.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    @Override
    public Company update(Company company) {
        log.debug("Request to update Company : {}", company);
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> partialUpdate(Company company) {
        log.debug("Request to partially update Company : {}", company);

        return companyRepository
            .findById(company.getId())
            .map(existingCompany -> {
                if (company.getCompanyName() != null) {
                    existingCompany.setCompanyName(company.getCompanyName());
                }
                if (company.getCompanyType() != null) {
                    existingCompany.setCompanyType(company.getCompanyType());
                }
                if (company.getCompanyDescription() != null) {
                    existingCompany.setCompanyDescription(company.getCompanyDescription());
                }
                if (company.getCompanyPicture() != null) {
                    existingCompany.setCompanyPicture(company.getCompanyPicture());
                }
                if (company.getCompanyCreationDate() != null) {
                    existingCompany.setCompanyCreationDate(company.getCompanyCreationDate());
                }
                if (company.getCompanyLocationAddress() != null) {
                    existingCompany.setCompanyLocationAddress(company.getCompanyLocationAddress());
                }
                if (company.getCompanyActivityDomaine() != null) {
                    existingCompany.setCompanyActivityDomaine(company.getCompanyActivityDomaine());
                }

                return existingCompany;
            })
            .map(companyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Company> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }
}
