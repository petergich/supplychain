package supplyChain.supplychain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.Production;
import supplyChain.supplychain.entities.RawMaterial;
import supplyChain.supplychain.entities.RawMaterialUsed;
import supplyChain.supplychain.repositories.RawMaterialUsedRepository;

import java.util.List;

@Service
public class RawMaterialUsedService {
    @Autowired
    RawMaterialUsedRepository rawMaterialUsedRepository;

    public List<RawMaterialUsed> findByProduction(Production production){
        return rawMaterialUsedRepository.findByProduction(production);
    }
    public String deleteRawMaterialUsed(Long id) throws Exception{
        RawMaterialUsed rawMaterialUsed = rawMaterialUsedRepository.findById(id).orElseThrow(()-> new Exception("Raw Material Used NOT FOUND"));
        rawMaterialUsedRepository.delete(rawMaterialUsed);
        return "Deleted";
    }
    public List<RawMaterialUsed> findByRawMaterial(RawMaterial rawMaterial){
        List<RawMaterialUsed> rawMaterialUseds = rawMaterialUsedRepository.findByRawMaterial(rawMaterial);
        return  rawMaterialUseds;
    }

}
