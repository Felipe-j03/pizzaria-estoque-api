package com.nostrapizza.estoque_api.adapter.in.web;

import com.nostrapizza.estoque_api.adapter.dto.request.RegisterProductRequest;
import com.nostrapizza.estoque_api.adapter.dto.request.UpdateProductRequest;
import com.nostrapizza.estoque_api.adapter.dto.response.ProductResponse;
import com.nostrapizza.estoque_api.application.port.in.*;
import com.nostrapizza.estoque_api.domain.entity.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeactivateProductUseCase deactivateProductUseCase;
    private final ListLowStockProductsUseCase listLowStockProductsUseCase;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody RegisterProductRequest request) {

        CreateProductCommand command = new CreateProductCommand(request.name(), request.unit(), request.currentQuantity(),
                request.minQuantity());
        Product product = createProductUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {

        List<Product> products = listProductsUseCase.execute();

        List<ProductResponse> productResponses = products.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
        Product product = getProductByIdUseCase.execute(id);
        return ResponseEntity.ok(toResponse(product));

    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request) {

        UpdateProductCommand command = new UpdateProductCommand(id, request.name(), request.unit(), request.minQuantity());

        Product product = updateProductUseCase.execute(command);

        return ResponseEntity.ok(toResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deactivateProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponse>> findLowStock() {

        List<Product> products = listLowStockProductsUseCase.execute();

        List<ProductResponse> productResponses = products.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(productResponses);
    }



    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getUnit(),
                product.getCurrentQuantity(),
                product.getMinQuantity(),
                product.isActive(),
                product.getCreatedAt()
        );
    }
}