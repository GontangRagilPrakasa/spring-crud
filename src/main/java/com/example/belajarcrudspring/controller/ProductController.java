package com.example.belajarcrudspring.controller;


import com.example.belajarcrudspring.domain.dto.ProductRequestDto;
import com.example.belajarcrudspring.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "product", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll()
    {
        return productService.getAll();
    }

    @RequestMapping(value = "product", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> save(@ModelAttribute ProductRequestDto productRequestDto)
    {
        return  productService.save(productRequestDto);
    }

    @RequestMapping(value = "product/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getById(@PathVariable("id") long id)
    {
        return  productService.getById(id);
    }

    @RequestMapping(value = "product/{id}", method = RequestMethod.PUT, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public  ResponseEntity<Object> update(@PathVariable("id") long id, @ModelAttribute ProductRequestDto productRequestDto)
    {
        return productService.update(id, productRequestDto);
    }

    @RequestMapping(value = "product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") long id)
    {
        return  productService.delete(id);
    }
}
