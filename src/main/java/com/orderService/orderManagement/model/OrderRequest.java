package com.orderService.orderManagement.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

        @NotBlank(message = "Customer name is required")
        private String customerName;
        @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than zero")
        private BigDecimal totalAmount;

        public @NotBlank(message = "Customer name is required") String getCustomerName() {
                return customerName;
        }

        public void setCustomerName(@NotBlank(message = "Customer name is required") String customerName) {
                this.customerName = customerName;
        }

        public @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than zero") BigDecimal getTotalAmount() {
                return totalAmount;
        }

        public void setTotalAmount(@DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than zero") BigDecimal totalAmount) {
                this.totalAmount = totalAmount;
        }
}
