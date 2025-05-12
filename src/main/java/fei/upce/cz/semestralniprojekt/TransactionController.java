package fei.upce.cz.semestralniprojekt;

import fei.upce.cz.semestralniprojekt.Transaction.TransactionType;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/")
    public String getAllTransactions(@ModelAttribute("filter") TransactionFilter filter,
            Model model) {
        if (filter == null) {
            filter = new TransactionFilter();
        }
        List<Transaction> transactions = transactionRepository.findWithFilters(
                filter.getFromDate(),
                filter.getToDate(),
                filter.getType(),
                filter.getCurrency()
        );

        model.addAttribute("transactions", transactions);
        model.addAttribute("transactionForm", new Transaction()); // Změna na transactionForm
        model.addAttribute("currencies", Transaction.Currency.values());

        double totalIncome = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.INCOME))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpenses = transactions.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENSE))
                .mapToDouble(Transaction::getAmount)
                .sum();

        model.addAttribute("totalIncome", String.format("%.2f", totalIncome));
        model.addAttribute("totalExpenses", String.format("%.2f", totalExpenses));
        model.addAttribute("balance", String.format("%.2f", totalIncome - totalExpenses));

        return "index";
    }

    @PostMapping("/add")
    public String addTransaction(@Valid @ModelAttribute("transactionForm") Transaction transactionForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("filter", new TransactionFilter());
            model.addAttribute("currencies", Transaction.Currency.values());
            return "index";
        }

        transactionRepository.save(transactionForm);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/export")
    public void exportToCSV(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) Transaction.TransactionType type,
            @RequestParam(required = false) Transaction.Currency currency,
            HttpServletResponse response) throws IOException {

        response.setContentType("text/csv; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=transakce.csv");

        // Připravíme české hlavičky
        String[] CSV_HEADERS = {"Datum", "Typ", "Kategorie", "Popis", "Částka", "Měna"};

        try (CSVPrinter csvPrinter = new CSVPrinter(
                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8),
                CSVFormat.DEFAULT
                        .withHeader(CSV_HEADERS)
                        .withDelimiter(';')
                        .withRecordSeparator("\r\n"))) {

            List<Transaction> transactions = transactionRepository.findWithFilters(
                    fromDate, toDate, type, currency);

            for (Transaction transaction : transactions) {
                csvPrinter.printRecord(
                        transaction.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        transaction.getType() == TransactionType.INCOME ? "Příjem" : "Výdaj",
                        transaction.getCategory(),
                        transaction.getDescription(),
                        transaction.getAmount(),
                        transaction.getCurrency().name()
                );
            }
        }
    }
}
