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
        purchaseRecord.setPgrId(fieldSet.readString(2));
        String wcsIdStr = fieldSet.readString(3);
        if (!wcsIdStr.isEmpty()) {
            purchaseRecord.setWcsId(Long.parseLong(wcsIdStr));
        }
        String dateString = fieldSet.readString(4);
        purchaseRecord.setPurchaseDate(LocalDate.parse(dateString, formatter));
        purchaseRecord.setSkuId(fieldSet.readLong(5));
        purchaseRecord.setSkuName(fieldSet.readString(6));
        purchaseRecord.setQuantity(fieldSet.readInt(7));
        purchaseRecord.setSkuPrice(fieldSet.readDouble(8));

        return purchaseRecord;
    }
}