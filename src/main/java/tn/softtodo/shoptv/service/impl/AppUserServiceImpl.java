package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.AppUser;
import tn.softtodo.shoptv.repository.AppUserRepository;
import tn.softtodo.shoptv.repository.UserRepository;
import tn.softtodo.shoptv.service.AppUserService;

/**
 * Service Implementation for managing {@link AppUser}.
 */
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

    private final AppUserRepository appUserRepository;

    private final UserRepository userRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository, UserRepository userRepository) {
        this.appUserRepository = appUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AppUser save(AppUser appUser) {
        log.debug("Request to save AppUser : {}", appUser);
        Long userId = appUser.getUser().getId();
        userRepository.findById(userId).ifPresent(appUser::user);
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser update(AppUser appUser) {
        log.debug("Request to update AppUser : {}", appUser);
        Long userId = appUser.getUser().getId();
        userRepository.findById(userId).ifPresent(appUser::user);
        return appUserRepository.save(appUser);
    }

    @Override
    public Optional<AppUser> partialUpdate(AppUser appUser) {
        log.debug("Request to partially update AppUser : {}", appUser);

        return appUserRepository
            .findById(appUser.getId())
            .map(existingAppUser -> {
                if (appUser.getAvatarUrl() != null) {
                    existingAppUser.setAvatarUrl(appUser.getAvatarUrl());
                }
                if (appUser.getStatus() != null) {
                    existingAppUser.setStatus(appUser.getStatus());
                }
                if (appUser.getRole() != null) {
                    existingAppUser.setRole(appUser.getRole());
                }

                return existingAppUser;
            })
            .map(appUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppUser> findAll(Pageable pageable) {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll(pageable);
    }

    public Page<AppUser> findAllWithEagerRelationships(Pageable pageable) {
        return appUserRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUser> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        return appUserRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);
        appUserRepository.deleteById(id);
    }
}
