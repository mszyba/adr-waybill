package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.Un;
import eu.michalszyba.adrwaybill.repository.UnRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
