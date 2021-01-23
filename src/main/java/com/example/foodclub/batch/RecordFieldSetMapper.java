package com.example.foodclub.batch;

import com.example.foodclub.model.Purchase;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.boot.context.properties.bind.BindException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordFieldSetMapper extends BeanWrapperFieldSetMapper<PurchaseRecord> {
 
    public PurchaseRecord mapFieldSet(FieldSet fieldSet) throws BindException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        PurchaseRecord purchaseRecord = new PurchaseRecord();
 
        purchaseRecord.setTransactionId(fieldSet.readLong(0));
        purchaseRecord.setPalsId(fieldSet.readString(1));
        String dateString = fieldSet.readString(2);
        purchaseRecord.setPurchaseDate(LocalDate.parse(dateString, formatter));
        purchaseRecord.setSkuId(fieldSet.readLong(3));
        purchaseRecord.setSkuName(fieldSet.readString(4));
        purchaseRecord.setSkuPrice(fieldSet.readDouble(5));

        return purchaseRecord;
    }
}