package com.example.belajarcrudspring.service;

import com.example.belajarcrudspring.domain.dao.ProductDao;
import com.example.belajarcrudspring.domain.dto.ProductDto;
import com.example.belajarcrudspring.domain.dto.ProductRequestDto;
import com.example.belajarcrudspring.repository.ProductRepository;
import com.example.belajarcrudspring.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<Object> getAll()
    {
        List<ProductDao> greetingDaoList = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();

        for (ProductDao dao : greetingDaoList) {
           ProductDto productDto = new ProductDto();
           productDto.setId(dao.getId());
           productDto.setProductName(dao.getProductName());
           productDtoList.add(productDto);
        }

        return ResponseUtil.build(
                true,
                HttpStatus.OK.value(),
                "Berhasil Ditemukan",
                productDtoList,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> save(ProductRequestDto request)
    {
        ProductDao productDao = new ProductDao();
        productDao.setProductName(request.getProductName());
        productDao.setProductDescription(request.getProductDescription());
        productRepository.save(productDao);

        ProductDto productDto = new ProductDto();
        productDto.setId(productDao.getId());
        productDto.setProductName(productDao.getProductName());

        return ResponseUtil.build(
                true,
                HttpStatus.CREATED.value(),
                "Berhasil Disimpan",
                productDto,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> getById(long id)
    {
        Optional<ProductDao> productDao = productRepository.findById(id);
        if (productDao.isPresent()) {
            return ResponseUtil.build(
                    true,
                    HttpStatus.OK.value(),
                    "Berhasil Ditemukan",
                    productDao,
                    HttpStatus.OK
            );
        } else {
            return ResponseUtil.build(
                    false,
                    HttpStatus.NOT_FOUND.value(),
                    "Tidak Berhasil Ditemukan",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public ResponseEntity<Object> update(long id, ProductRequestDto requestDto)
    {
        Optional<ProductDao> productDao = productRepository.findById(id);
        if (productDao.isPresent()) {
            ProductDao productDao1 = productDao.get();
            productDao1.setProductName(requestDto.getProductName());
            productDao1.setProductDescription(requestDto.getProductDescription());
            productRepository.save((productDao1));

            ProductDto productDto = new ProductDto();
            productDto.setId(productDao1.getId());
            productDto.setProductName(productDao1.getProductName());

            return ResponseUtil.build(
                    true,
                    HttpStatus.OK.value(),
                    "Berhasil Ditemukan",
                    productDto,
                    HttpStatus.OK
            );
        } else {
            return ResponseUtil.build(
                    false,
                    HttpStatus.NOT_FOUND.value(),
                    "Tidak Berhasil Ditemukan",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public ResponseEntity<Object> delete(long id)
    {
        Optional<ProductDao> productDao = productRepository.findById(id);
        if (productDao.isPresent()) {
            productRepository.deleteById(id);
            return ResponseUtil.build(
                    true,
                    HttpStatus.OK.value(),
                    "Berhasil Dihapus",
                    null,
                    HttpStatus.OK
            );
        } else {
            return ResponseUtil.build(
                    false,
                    HttpStatus.NOT_FOUND.value(),
                    "Tidak Berhasil Dihapus",
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

    }
}
