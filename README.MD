# Belajar Spring Boot Create, Read, Update, Delete

## Persiapan
Apa yang harus dipersiapakan

| Application | README |
| ------ | ------ |
| GitHub | [https://github.com/][PlGh] |
| Mysql Driver | [https://www.mysql.com/downloads/][PlGh] |
| JDK (verison 11) | [https://www.oracle.com/java/technologies/downloads/][PlGh] |
| Inyellj | [https://www.jetbrains.com/idea/][PlGh] |

## Mulai Tutorialnya

Setelah semuanya terinstall, silahkan mengikuti tutorial dibawah ini

Pertama membuat project spring boot terlebih dahulu dengan kunjungi link ini
untuk membuat project baru dengan dependency Spring Web dan Mysql Driver
```sh
https://start.spring.io/ 
```
```java
    <dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<scope>runtime</scope>
	</dependency>

	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<version>1.18.24</version>
		<scope>provided</scope>
	</dependency>
```
Masukkan settingan environtment terlebih dahulu pada file `src/main/resources/application.properties`
```java
server.port=3056
server.servlet.context-path=/api

spring.datasource.url= jdbc:mysql://localhost:3306/namadatabase?useSSL=false
spring.datasource.username= root
spring.datasource.password= root
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= update
```

Jika dirasa sudah sekarang saatnya untuk eksekusi (untuk case ini saya akan menggunakan study kasus berdasarkan pengalaman saya). Silahkan membuat file class pada folder `domain.response.ApiResponse` dan `util.ResponseUtil`, class ini berfungsi untuk generate global class pada saat pengembalian data response
domain/response/ApiResponse
```java
public class ResponseUtil {
   @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = -142050505730444504L;
    private Boolean status;
    private String message;
    private int statusCode;
    private T data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

```
Util/ResponseUtil
```java

public class ResponseUtil {
    // buat constructor terbelbih dahulu
    public ResponseUtil() {
    }

    public static <T>ResponseEntity<Object> build(Boolean status,int statusCode, String message,  T data,HttpStatus httpStatus) {
        return new ResponseEntity<>(build(status, statusCode ,message, data), httpStatus);
    }

    private static <T>ApiResponse<T> build(Boolean status, int statusCode, String message,  T data) {
        ApiResponse response = new ApiResponse();
        response.setStatus(status);
        response.setMessage(message);
        response.setStatusCode(statusCode);
        response.setData(data);
        return  response;
    }
}
```

Selanjutnya adalah membuat skema study case, untuk tutorial kali ini saya hanya menggunakan 1 table Product
| Field | Type |
|---|---|
| **id** | INT (*Primary Key*) |
| product_name | VARCHAR(255) |
| product_description | VARCHAR(255) |

Sekarang saya akan simpan schema database diatas dengan table Product didalam file DAO
`domain.dao.ProductDao`

```java
@Entity
@Table(name = "Products")
public class ProductDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;
    public ProductDao() {
    }
    public ProductDao(String product_name, String product_description)
    {
        this.productName = product_name;
        this.productDescription = product_description;
    }

    public long getId(){
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductName(String product_name) {
        this.productName = product_name;
    }

    public void setProductDescription(String product_description) {
        this.productDescription = product_description;
    }
}
```
Selanjutnya saya akan membuat DTO untuk menampilkan data `domain.dto.ProdcutDto` dan mengambil data `domain.dto.ProdcutRequestDto` (ketika request (POST dan PUT)
domain/dto/ProdcutDto
```java
public class ProductDto {

    public ProductDto(){}
    private long id;
    private String productName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
```
domain/dto/ProdcutRequestDto
```java
public class ProductRequestDto {
    private String productName;
    private String productDescription;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
```
Langkah selanjutnya adalah membaut folder repository terlebih dahulu `repository.ProdcutRepository`
untuk membuat interface pemanggilan data JpaRepository
```java
public interface ProductRepository extends JpaRepository<ProductDao, Long> {
   // bisa custome kalau pengen
}
```
Selanjutnya adalah membuat folder services `services.ProductServices` untuk membuat logic data
```java
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
```
Langkah terakhir adalah membuat folder controller untuk membuat rest API `controller.ProductController`
```java
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

```
Silahkan running dengan port yang sudah anda setting diawal tadi
```xml
server.port=3056
server.servlet.context-path=/api
```




 

