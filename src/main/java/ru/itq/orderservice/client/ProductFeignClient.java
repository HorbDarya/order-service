package ru.itq.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itq.orderservice.config.FeignClientConfig;
import ru.itq.orderservice.dto.ProductDto;

@FeignClient(name = "product-service", url = "http://localhost:8080/product-service/products",  configuration = FeignClientConfig.class)
public interface ProductFeignClient {
    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);
}
