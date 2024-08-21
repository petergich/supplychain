package supplyChain.supplychain.event_listeners;

import jakarta.persistence.PostPersist;
import org.springframework.stereotype.Component;
import supplyChain.supplychain.entities.RawMaterialOrder;

@Component
public class RawMaterialEvents {

    @PostPersist
    public void postPersist(RawMaterialOrder material){
        System.err.println("COMMITTED DATA<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.err.println(material);
    }
}
