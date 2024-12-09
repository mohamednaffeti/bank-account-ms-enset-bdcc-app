package net.youssfi.customerservice.web;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import net.youssfi.customerservice.entities.Customer;
import net.youssfi.customerservice.repository.CustomerRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CustomerRestController {
    private CustomerRepository customerRepository;
    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @GetMapping("/customers")
    @Cacheable(value = "customerCache",key = "#root.methodName")
    @RateLimiter(name = "exampleService", fallbackMethod = "rateLimiterFallback")
    public List<Customer> customerList(){
        log.info("hello getAll from database");
        return customerRepository.findAll();
    }
    @GetMapping("/customers/{id}")
    @Cacheable(value = "customerCache",key = "#id")
    public Customer customerById(@PathVariable Long id){
        log.info("hello getById from database");
        return customerRepository.findById(id).get();
    }

    @PostMapping("/add")
    @CacheEvict(value = "customerCache", allEntries = true)
    public Customer addCustomer(@RequestBody Customer customer){

        return customerRepository.save(customer);
    }
    public List<Customer> rateLimiterFallback(Throwable throwable) {
        log.info("too many request");
        return List.of();  // Renvoie une liste vide
    }
 }
