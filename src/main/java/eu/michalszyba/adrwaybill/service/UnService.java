package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.Un;
import eu.michalszyba.adrwaybill.repository.UnRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnService {

    private final UnRepository unRepository;

    public UnService(UnRepository unRepository) {
        this.unRepository = unRepository;
    }

    public List<Un> getAllUn() {
        return unRepository.findAll();
    }

    public Un getUnById(Long id) {
        return unRepository.findById(id)
                .orElse(null);
    }

    public List<Un> getAllUnByUnNumber(String unNumber) {
        return unRepository
                .findAllByUnNumberIsStartingWith(unNumber)
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }
}
