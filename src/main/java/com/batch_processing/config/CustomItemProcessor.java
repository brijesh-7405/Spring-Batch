package com.batch_processing.config;

import com.batch_processing.model.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessor implements ItemProcessor<Product,Product> {
    @Override
    public Product process(Product item) throws Exception {
        try {
//            put the percentage logic
            System.out.println(item.getDescription());
            float discountPer = Float.parseFloat(item.getDiscount().trim());
            float originalPrice = Float.parseFloat(item.getPrice().trim());
            float discount = (discountPer / 100) * originalPrice;
            float finalPrice = originalPrice - discount;
            item.setDiscountedPrice(String.valueOf(finalPrice));
        } catch (
                NumberFormatException ex
        ) {
            ex.printStackTrace();
        }
        return item;
    }
}
