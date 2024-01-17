package com.ecommerce.reportservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "report_item")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class ReportItem extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportItemId;

    @NotBlank(message = "Identifier is required")
    private String identifier;

    @NotNull(message = "Gross sales is required")
    @Min(value = 0, message = "Gross sales must be greater than or equal to 0")
    private float grossSales;

    @NotNull(message = "Net sales is required")
    @Min(value = 0, message = "Net sales must be greater than or equal to 0")
    private float netSales;

    @NotNull(message = "Orders count is required")
    @Min(value = 0, message = "Orders count must be greater than or equal to 0")
    private int ordersCount;

    @NotNull(message = "Products count is required")
    @Min(value = 0, message = "Products count must be greater than or equal to 0")
    private int productsCount;

    public ReportItem(String identifier) {
        this.identifier = identifier;
    }

    public ReportItem(String identifier, float grossSales, float netSales) {
        this.identifier = identifier;
        this.grossSales = grossSales;
        this.netSales = netSales;
    }

    public ReportItem(String identifier, float grossSales, float netSales, int productsCount) {
        super();
        this.identifier = identifier;
        this.grossSales = grossSales;
        this.netSales = netSales;
        this.productsCount = productsCount;
    }

    public void addGrossSales(float amount) {
        this.grossSales += amount;
    }

    public void addNetSales(float amount) {
        this.netSales += amount;
    }

    public void increaseOrdersCount() {
        this.ordersCount++;
    }

    public void increaseProductsCount(int count) {
        this.productsCount += count;
    }
}
