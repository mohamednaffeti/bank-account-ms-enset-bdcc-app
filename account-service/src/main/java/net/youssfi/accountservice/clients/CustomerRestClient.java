package net.youssfi.accountservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import net.youssfi.accountservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cache.annotation.Cacheable;
import java.util.Collections;
import java.util.List;
@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {
    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "CustomerServiceCB",fallbackMethod = "getDefaultCustomer")
    @Retry(name = "CustomerServiceRetry", fallbackMethod = "getDefaultCustomer")
    @Cacheable(value = "customerCache", key = "#id")  // La clé du cache est basée sur l'ID du customer
    Customer findCustomerById(@PathVariable Long id);

    @GetMapping("/customers")
    @CircuitBreaker(name = "CustomerServiceCB",fallbackMethod = "getDefaultCustomers")
    @Retry(name = "CustomerServiceRetry", fallbackMethod = "getDefaultCustomers")
    @Cacheable(value = "customerCache",key = "#root.methodName")  // La liste entière des customers peut être mise en cache avec une clé globale
    List<Customer> allCustomers();


    default Customer getDefaultCustomer(Long id, Exception e){
        return Customer.builder()
                .id(id)
                .firstName("Defualt firstName")
                .lastName("Default LastName")
                .email("Default Email")
                .build();
    }
    default List getDefaultCustomers(Exception e){
        return Collections.EMPTY_LIST;
    }



}
