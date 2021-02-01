package com.example.foodclub.batch;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRecord {

    private long transactionId;

    private String palsId;

    private String pgrId;

    private long wcsId;

    private LocalDate purchaseDate;

    private long skuId;

    private String skuName;

    private int quantity;

    private double skuPrice;


}
