package com.example.foodclub.service;

import com.example.foodclub.error.ResourceNotFoundException;
import com.example.foodclub.model.UserKey;
import com.example.foodclub.model.Usermap;
import com.example.foodclub.repository.UserKeyRepository;
import com.example.foodclub.repository.UsermapRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class UserService {

    private final UsermapRepository usermapRepository;
    private final UserKeyRepository userKeyRepository;

    public Usermap findUserByIds(String palsId, String pgrId, long wcsId) throws ResourceNotFoundException {

        Usermap usermap = null;
        if (pgrId != null && !pgrId.isEmpty()) {
            usermap = usermapRepository.findByUserKeysPgrId(pgrId)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }

        if (usermap == null && wcsId != 0) {
            usermap = usermapRepository.findByUserKeysWcsId(wcsId)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }

        if (usermap == null && palsId != null && !palsId.isEmpty()) {
            usermap = usermapRepository.findByUserKeysPalsId(palsId)
                    .stream()
                    .findFirst()
                    .orElse(null);
            log.info("usermap: {}", usermap);
        }

        if (usermap != null) {
            usermap.addUserKey(new UserKey(palsId, pgrId, wcsId));
        }

        usermapRepository.save(usermap);

        return usermap;
    }

}
