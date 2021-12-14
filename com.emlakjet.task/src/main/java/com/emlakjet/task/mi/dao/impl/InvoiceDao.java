package com.emlakjet.task.mi.dao.impl;

import com.emlakjet.task.mi.dao.Dao;
import com.emlakjet.task.mi.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn("dataSource")
public class InvoiceDao implements Dao<Invoice> {

    @Autowired
    DataSource dataSource;

    public InvoiceDao( ) {
    }

    @Override
    public Invoice get(long id) {
        Invoice result = null;
        String sql = "SELECT * FROM emlakjetdb.invoices where id = ?";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            long record_id = resultSet.getLong("ID");
            float amount = resultSet.getFloat("AMOUNT");
            String invoiceId = resultSet.getString("INVOICE_ID");
            String description = resultSet.getString("DESCRIPTION");
            long userid =  resultSet.getLong("USER_ID");
            result = new Invoice(description, invoiceId, record_id, amount, userid);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Invoice> getAll() {
        List<Invoice> result = new ArrayList<>();
        String sql = "SELECT * FROM emlakjetdb.invoices";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long record_id = resultSet.getLong("ID");
                float amount = resultSet.getFloat("AMOUNT");
                String invoiceId = resultSet.getString("INVOICE_ID");
                String description = resultSet.getString("DESCRIPTION");
                long userid =  resultSet.getLong("USER_ID");
                long is =  resultSet.getLong("ISAPPROVED");
                result.add(new Invoice(description, invoiceId, record_id, amount, userid));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    public Invoice save(Invoice newInvoice) {
        String sql = "INSERT INTO emlakjetdb.invoices (  user_id, inserted_date, description, invoice_Id, amount, isApproved) VALUES(  ?, SYSDATE(), ?, ?, ?,?);";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);

            preparedStatement.setLong(1, newInvoice.getUserId());
            preparedStatement.setString(2, newInvoice.getDescription());
            preparedStatement.setString(3, newInvoice.getInvoiceId());
            preparedStatement.setFloat(4, newInvoice.getAmount());
            preparedStatement.setBoolean(5, newInvoice.isApproved());

            int rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
        return newInvoice;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE  emlakjetdb.invoices where id = ?";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public float getInvoiceTotalOfUser(long userid){
        float result=0;
        String sql = "SELECT SUM(amount) as total FROM emlakjetdb.invoices where user_id = ? and isApproved = 1";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getFloat("TOTAL");

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }
}
