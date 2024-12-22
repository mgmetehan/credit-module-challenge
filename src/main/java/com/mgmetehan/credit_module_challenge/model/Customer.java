package com.mgmetehan.credit_module_challenge.model;

import com.mgmetehan.credit_module_challenge.model.core.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "CUSTOMERS")
@Entity
@SQLDelete(sql = "UPDATE CUSTOMERS SET deleted = true WHERE id = ? AND version = ?")
public class Customer extends BaseEntity {
    private String name;
    private String surname;
    private BigDecimal creditLimit;
    private BigDecimal usedCreditLimit;
    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Loan> loans;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
        this.usedCreditLimit = BigDecimal.ZERO;
    }
}
