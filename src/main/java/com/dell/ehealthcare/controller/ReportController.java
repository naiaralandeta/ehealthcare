package com.dell.ehealthcare.controller;

import com.dell.ehealthcare.dto.StockDTO;
import com.dell.ehealthcare.exceptions.UserNotfoundException;
import com.dell.ehealthcare.model.Cart;
import com.dell.ehealthcare.model.Medicine;
import com.dell.ehealthcare.model.enums.ReportRange;
import com.dell.ehealthcare.payload.response.MessageResponse;
import com.dell.ehealthcare.services.CartService;
import com.dell.ehealthcare.services.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class ReportController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private CartService cartService;

    @GetMapping("/report")
    public ResponseEntity<?> getReport(@RequestParam("type") String reportType, @RequestParam("range") Integer range, @RequestParam("date") ZonedDateTime start){

        ReportRange rangeType = ReportRange.values()[range];
        switch (rangeType){
            case WEEKLY:
                ZonedDateTime end = start.plusDays(7);
                switch (reportType){
                    case "STOCK":
                        List<Medicine> medicines = medicineService.findByBetween(start, end);
                        List<StockDTO> stock = new ArrayList<>();
                        for (Medicine medicine: medicines) {
                            stock.add(new StockDTO(medicine.getId(), medicine.getName(), medicine.getQuantity()));
                        }
                        return ResponseEntity.ok(stock);
                    case "SALES":
                        return ResponseEntity.ok(cartService.findByBetween(start, end));
                    case "MEDICINE":
                        return ResponseEntity.ok(medicineService.findByBetween(start, end));
                    default:
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Report couldn't process!"));
                }
            case MONTHLY:
                switch (reportType){
                    case "STOCK":
                        List<Medicine> medicines = medicineService.findByMonth(start);
                        List<StockDTO> stock = new ArrayList<>();
                        for (Medicine medicine: medicines) {
                            stock.add(new StockDTO(medicine.getId(), medicine.getName(), medicine.getQuantity()));
                        }
                        return ResponseEntity.ok(stock);
                    case "SALES":
                        return ResponseEntity.ok(cartService.findByMonth(start));
                    case "MEDICINE":
                        return ResponseEntity.ok(medicineService.findByMonth(start));
                    default:
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Report couldn't process!"));
                }
            case YEARLY:
                switch (reportType){
                    case "STOCK":
                        List<Medicine> medicines = medicineService.findByYear(start);
                        List<StockDTO> stock = new ArrayList<>();
                        for (Medicine medicine: medicines) {
                            stock.add(new StockDTO(medicine.getId(), medicine.getName(), medicine.getQuantity()));
                        }
                        return ResponseEntity.ok(stock);
                    case "SALES":
                        return ResponseEntity.ok(cartService.findByYear(start));
                    case "MEDICINE":
                        return ResponseEntity.ok(medicineService.findByYear(start));
                    default:
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Report couldn't process!"));
                }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Report couldn't process!"));
    }

    @GetMapping("/user-report")
    public ResponseEntity<?> getUsersReport(@RequestParam("userId") Long id){
        List<Cart> orders = cartService.getAllOrders(id);

        if(orders.isEmpty()){
            throw new UserNotfoundException();
        }

        return ResponseEntity.ok(orders);
    }
}
