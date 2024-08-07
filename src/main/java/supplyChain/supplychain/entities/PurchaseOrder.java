package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
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
    private LocalDate date;
    @NotNull
    private String poNumber;
    boolean delivered = false;


    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }
}
