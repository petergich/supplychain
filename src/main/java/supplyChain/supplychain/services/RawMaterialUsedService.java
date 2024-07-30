package supplyChain.supplychain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.repositories.RawMaterialUsedRepository;

@Service
public class RawMaterialUsedService {
    @Autowired
    RawMaterialUsedRepository rawMaterialUsedRepository;
}
