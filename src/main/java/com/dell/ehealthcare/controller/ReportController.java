package com.dell.ehealthcare.controller;

import com.dell.ehealthcare.dto.StockDTO;
import com.dell.ehealthcare.model.Cart;
import com.dell.ehealthcare.model.Medicine;
import com.dell.ehealthcare.model.enums.*;
import com.dell.ehealthcare.payload.response.MessageResponse;
import com.dell.ehealthcare.services.CartService;
import com.dell.ehealthcare.services.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private MedicineService medicineService;

    private CartService cartService;

    @GetMapping("/report")
    public ResponseEntity<?> getReport(@RequestParam("type") Integer type, @RequestParam("range") Integer range, @RequestParam("date") ZonedDateTime start){

        ReportRange reportrange = ReportRange.values()[range];
        ReportType reportType = ReportType.values()[type];

        switch (reportrange){
            case WEEKLY:
                ZonedDateTime end = start.plusDays(7);
                switch (reportType){
                    case STOCK:
                        List<Medicine> meds = medicineService.findByBetween(start, end);
                        List<StockDTO> stock = new ArrayList<>();
                        for (Medicine medicine: meds) {
                            stock.add(new StockDTO(medicine.getId(), medicine.getName(), medicine.getQuantity()));
                        }
                        if(!stock.isEmpty()){
                            return new ResponseEntity<>(stock, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    case SALES:
                        List<Cart> carts = cartService.findByBetween(start, end);
                        if(!carts.isEmpty()){
                            return new ResponseEntity<>(carts, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    case MEDICINE:
                        List<Medicine> medicines = medicineService.findByBetween(start, end);
                        if(!medicines.isEmpty()){
                            return new ResponseEntity<>(medicines, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    default:
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Report couldn't process!"));
                }
            case MONTHLY:
                switch (reportType){
                    case STOCK:
                        List<Medicine> meds = medicineService.findByMonth(start);
                        List<StockDTO> stock = new ArrayList<>();
                        for (Medicine medicine: meds) {
                            stock.add(new StockDTO(medicine.getId(), medicine.getName(), medicine.getQuantity()));
                        }
                        if(!stock.isEmpty()){
                            return new ResponseEntity<>(stock, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    case SALES:
                        List<Cart> carts = cartService.findByMonth(start);
                        if(!carts.isEmpty()){
                            return new ResponseEntity<>(carts, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    case MEDICINE:
                        List<Medicine> medicines = medicineService.findByMonth(start);
                        if(!medicines.isEmpty()){
                            return new ResponseEntity<>(medicines, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    default:
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Report couldn't process!"));
                }
            case YEARLY:
                switch (reportType){
                    case STOCK:
                        List<Medicine> meds = medicineService.findByYear(start);
                        List<StockDTO> stock = new ArrayList<>();
                        for (Medicine medicine: meds) {
                            stock.add(new StockDTO(medicine.getId(), medicine.getName(), medicine.getQuantity()));
                        }
                        if(!stock.isEmpty()){
                            return new ResponseEntity<>(stock, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    case SALES:
                        List<Cart> carts = cartService.findByYear(start);
                        if(!carts.isEmpty()){
                            return new ResponseEntity<>(carts, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    case MEDICINE:
                        List<Medicine> medicines = medicineService.findByYear(start);
                        if(!medicines.isEmpty()){
                            return new ResponseEntity<>(medicines, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                        }
                    default:
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Report couldn't process!"));
                }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Report couldn't process!"));
    }

    @GetMapping("/user-report")
    public ResponseEntity<?> getUsersReport(@RequestParam("userId") Long id){
        List<Cart> orders = cartService.getAllOrders(id, OrderStatus.PENDING);

        if(!orders.isEmpty()){
            return ResponseEntity.ok(orders);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
