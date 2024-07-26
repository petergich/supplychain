package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;
    @ManyToOne
    @JoinColumn( name = "supplierName",nullable = false)
    private Supplier supplier;

    @NotNull
    private Date date;

    @NotNull
    private Integer poNumber;


    public Supplier getSupplier() {
        return supplier;
    }

    public Long getId() {
        return id;
    }

    public @NotNull Date getDate() {
        return date;
    }

    public @NotNull Integer getPoNumber() {
        return poNumber;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setDate(@NotNull Date date) {
        this.date = date;
    }

    public void setPoNumber(@NotNull Integer poNumber) {
        this.poNumber = poNumber;
    }
}
