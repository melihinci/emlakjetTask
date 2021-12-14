package com.emlakjet.task.mi.controller;

import com.emlakjet.task.mi.AppProperties;
import com.emlakjet.task.mi.dao.impl.InvoiceDao;
import com.emlakjet.task.mi.dao.impl.UserDao;
import com.emlakjet.task.mi.model.Invoice;
import com.emlakjet.task.mi.model.common.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/Invoice")
class InvoiceController {

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AppProperties appProperties;

    @GetMapping("/invoices")
    List<Invoice> all() {
        return invoiceDao.getAll();
    }

    @PostMapping("/invoices")
    AppResponse newInvoice(@RequestBody Invoice newInvoice, @RequestParam String name, @RequestParam String surname, @RequestParam String email, HttpServletResponse response) {
        long userid= userDao.getUserId(name,surname,email);
        float userTotal=invoiceDao.getInvoiceTotalOfUser(userid);
        newInvoice.setUserId(userid);
        AppResponse appResponse ;
        if((userTotal + newInvoice.getAmount())<=appProperties.getLimit()){
            newInvoice.setApproved(true);
            response.setStatus(HttpServletResponse.SC_OK);
            appResponse=new AppResponse(true,"Fatura kabul edildi");
        }else{
            newInvoice.setApproved(false);
            appResponse=new AppResponse(false,"Fatura reddedildi!");
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        invoiceDao.save(newInvoice);
        return appResponse;
    }

    @GetMapping("/invoices/{id}")
    Invoice one(@PathVariable long id) {
        return invoiceDao.get(id);

    }

    @DeleteMapping("/invoices/{id}")
    void deleteInvoice(@PathVariable int id) {
        invoiceDao.delete(id);
    }
}