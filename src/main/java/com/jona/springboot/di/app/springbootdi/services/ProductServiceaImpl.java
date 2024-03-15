package com.jona.springboot.di.app.springbootdi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jona.springboot.di.app.springbootdi.models.Product;
import com.jona.springboot.di.app.springbootdi.repositories.ProductRepository;

@Service
public class ProductServiceaImpl implements ProductService{

    
    // Inyeccion de dependencia mediante atributo.
    // @Autowired
    private ProductRepository repository;

    // Inyeccion de dependencia mediante setter.
    // @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    // Inyeccion de dependencia mediante constructor.
    // No es necesario usar la anotación @Autowired
    public ProductServiceaImpl(@Qualifier("productFoo") ProductRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().map(p -> {
            Double priceTax = p.getPrice() * 1.25d;
            // Product newProd = new Product(p.getId(), p.getName(), priceTax.longValue());
            Product newProd = (Product) p.clone();
            newProd.setPrice(priceTax.longValue());
            return newProd;
        }).collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id);
    }

}
