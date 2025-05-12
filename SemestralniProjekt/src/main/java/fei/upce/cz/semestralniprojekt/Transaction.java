package fei.upce.cz.semestralniprojekt;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Transaction {
    public enum TransactionType {
        INCOME, EXPENSE
    }

    public enum Currency {
        CZK("Kč"), USD("$"), EUR("€");
        
        private final String symbol;
        
        Currency(String symbol) {
            this.symbol = symbol;
        }
        
        public String getSymbol() {
            return symbol;
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Popis je povinný")
    private String description;
    
    @Positive(message = "Částka musí být kladné číslo")
    private double amount;
    
    private LocalDate date = LocalDate.now();
    
    @NotBlank(message = "Kategorie je povinná")
    private String category;
    
    @Enumerated(EnumType.STRING)
    private TransactionType type = TransactionType.EXPENSE;
    
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.CZK;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }
}