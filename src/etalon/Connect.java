/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etalon;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class Connect {
    private Connection connect = null;
    public Connection Conn () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/etalon?serverTimezone=UTC&characterEncoding=cp1251", "root", "yatskiv");
            return connect;
        }catch(Exception err){
            System.out.print(err);
        }
        return connect;
    }
}
