package fei.upce.cz.semestralniprojekt;

import java.time.LocalDate;

public class TransactionFilter {
    private LocalDate fromDate;
    private LocalDate toDate;
    private Transaction.TransactionType type;
    private Transaction.Currency currency;
    
    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
    public Transaction.TransactionType getType() { return type; }
    public void setType(Transaction.TransactionType type) { this.type = type; }
    public Transaction.Currency getCurrency() { return currency; }
    public void setCurrency(Transaction.Currency currency) { this.currency = currency; }
}