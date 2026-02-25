package az.edu.ada.wm2.lab4.controller;

import az.edu.ada.wm2.lab4.model.Product;
import az.edu.ada.wm2.lab4.service.ProductService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

     private final ProductService productService;

     public ProductController(ProductService productService){
         this.productService=productService;
     }

     @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
         return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
     }

     @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
         return ResponseEntity.ok(productService.getAllProducts());
     }
    @GetMapping("/{id}")
    public  ResponseEntity<Product> getProductById(@PathVariable UUID id){
        try{
            return ResponseEntity.ok(productService.getProductById(id));
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter/expiring")
    public ResponseEntity<List<Product>> getProductsExpiringBefore(@RequestParam LocalDate date) {
        return ResponseEntity.ok(productService.getProductsExpiringBefore(date));
    }

    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(min, max));
    }

}