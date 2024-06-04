package ro.uaic.info.parcel.database_management_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.repository.AwbRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AwbService {
    @Autowired
    private AwbRepository awbRepository;

    public List<Awb> getAllAwbs() {
        return StreamSupport.stream(awbRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Awb getAwbById(Long id) {
        Optional<Awb> awbById = awbRepository.findById(id);
        return awbById.orElse(null);
    }

    public Awb getAwbByUniqueNumber(String uniqueNumber) {
        return awbRepository.findByUniqueNumber(uniqueNumber);
    }

    public Awb addOrUpdateAwb(Awb awb) {
        return awbRepository.save(awb);
    }
}
