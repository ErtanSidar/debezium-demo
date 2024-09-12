package com.essoft.debezium.kafka;

import com.essoft.debezium.model.ProductMessageCDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics="product.public.product", groupId = "product.group")
    public void debeziumListener(ProductMessageCDC productMessageCDC) {
        if (productMessageCDC.getOp().equals("c")) {
            int tempStock = productMessageCDC.getAfter().getStock();
            logger.info("Change taken from debezium : Stock : {}", tempStock);
        }

        if (productMessageCDC.getOp().equals("u")) {
            if (productMessageCDC.getAfter().getStock() == 0) {
                logger.info("Change taken from debezium : Stock : dropped {} from {} Please click for when product is available.",
                        productMessageCDC.getBefore().getStock(), productMessageCDC.getAfter().getStock());
            }
        }
    }
}
