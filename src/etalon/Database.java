/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etalon;

import java.sql.*;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class Database {
    Connection connect;
    
    public Database(Connection connect){
        this.connect = connect;
    }
    
    public void addProduct(String name, int count, int price, String place) {
        try{
            String query = "INSERT INTO etalon (name, count, price, place) values (?, ?, ?, ?)";
            PreparedStatement ps = connect.prepareStatement(query);
            
            ps.setString(1, name);
            ps.setInt(2, count);
            ps.setInt(3, price);
            ps.setString(4, place);
            
            ps.execute();
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
    public void getAll(JTable table) {
        try{
            String query = "SELECT * FROM etalon ORDER BY name";
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            String id, name, place, count, price;
            while(rs.next()){
                id = rs.getString(1);
                name = rs.getString(2);
                count = rs.getString(3);
                price = rs.getString(4);
                place = rs.getString(5);
                String[] row = {id, name, count, price, place};
                model.addRow(row);
            }
            st.close();
            
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
    public void search(JTextField search, JTable table){
        try{
            if(search.getText().length() == 0){
                getAll(table);
            }
            if(search.getText().length() >= 3){
                String query = "SELECT * FROM etalon WHERE name LIKE '%"+search.getText()+"%'";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                String id, name, place, count, price;
                while(rs.next()){
                    id = rs.getString(1);
                    name = rs.getString(2);
                    count = rs.getString(3);
                    price = rs.getString(4);
                    place = rs.getString(5);
                    String[] row = {id, name, count, price, place};
                    model.addRow(row);
                }
                st.close();
            }
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
    public void addCount(int id, int count){
        try{
            String query = "UPDATE etalon SET count = "+count+" WHERE id = "+id+"";
            PreparedStatement ps = connect.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
    public void editProduct(String name, int count, int price, String place, int id) {
        try{
            String query = "UPDATE etalon SET name=?, count=?, price=?, place=? WHERE id=?";
            PreparedStatement ps = connect.prepareStatement(query);
            
            ps.setString(1, name);
            ps.setInt(2, count);
            ps.setInt(3, price);
            ps.setString(4, place);
            ps.setInt(5, id);
            ps.executeUpdate();
            ps.close();
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
    public void sellProduct(String name, int count){
        try{
            String query = "UPDATE etalon SET count= (SELECT count WHERE name = ?) - ? WHERE name = ?";
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, count);
            ps.setString(3, name);
            ps.executeUpdate();
            ps.close();
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
    public String getPrice(String name){
        try{
            String query = "SELECT price FROM etalon WHERE name = '"+name+"';";
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                return rs.getString(1);
            }
        }catch(Exception err){
            System.out.println(err);
        }
        return null;
    }
}
