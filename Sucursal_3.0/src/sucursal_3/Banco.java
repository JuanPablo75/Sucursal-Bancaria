
package sucursal_3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Banco { //HE INTENTADO USAR LO MINIMO POSIBLE LAS CUENTAS DE LA APP Y TRABAJAR TODO LO POSIBLE CON LA BASE DE DATOS
    
    private String nombreBanco;
    private List<Cuenta> ListaCuentasBanco = new ArrayList();
    
    public Banco(String nombre){
        this.nombreBanco = nombre;
        }
    public boolean Ingresar(String dni, float cantidad){
        
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:ifp.db");
            PreparedStatement stm = conexion.prepareStatement("update cuenta set saldo = saldo + "
                + cantidad + " where dni = '" + dni + "'");
            
            stm.executeUpdate();
            stm.close();
            conexion.close();
            
            return true;
            }
            catch(ClassNotFoundException ex){
                System.out.println("error" + ex);
                return false;
            }
            catch(SQLException ex){
                System.out.println("error" + ex);
                return false;
            }
    }
    public boolean Extraer(String dni , float cantidad){
        
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:ifp.db");
            Statement stm = conexion.createStatement();
            ResultSet rs = stm.executeQuery ("select * from cuenta where dni = '"+dni+"'");
            
            float saldo = rs.getFloat("saldo");
            
            if(saldo - cantidad < 0){
                rs.close();
                stm.close();
                conexion.close();
                return false;
                }
            else{
                rs.close();
                stm.close();
                
                PreparedStatement stm2 = conexion.prepareStatement("update cuenta set saldo = saldo - "
                    + cantidad + " where dni = '" + dni + "'");

                stm2.executeUpdate();
                stm2.close();
                conexion.close();

                return true;
                }
            }
        catch(ClassNotFoundException ex){
            System.out.println("error" + ex);
            return false;
            }
        catch(SQLException ex){
            System.out.println("error" + ex);
            return false;
            }
    }
    public boolean Transferencia(String dniOrigen,String dniDestino,float cantidad){
        
        if(Extraer(dniOrigen,cantidad)){
            
            Ingresar(dniDestino,cantidad);
            return true;
            }
        return false;
    }
    public List<Cuenta> getListDB(){ //OBTENGO LISTADO DE CUENTAS DE MI BASE DE DATOS "PRINCIPAL"
        
        List<Cuenta> cuentas = new ArrayList<>();
        
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:ifp.db");
            Statement stm = conexion.createStatement();
            
            ResultSet rs = stm.executeQuery ("select * from cuenta");
            
            while(rs.next()){
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                float saldo = rs.getFloat("saldo");
                java.sql.Date fechaSQL = rs.getDate("fecha");
                LocalDate fechaLD = fechaSQL.toLocalDate();
                
                Cuenta c = new Cuenta (dni,nombre);
                c.setFecha(fechaLD);
                c.setSaldo(saldo);
                cuentas.add(c);
                }
            rs.close();
            stm.close();
            conexion.close();
            }
        catch(ClassNotFoundException ex){
            System.out.println("error" + ex);
            }
        catch(SQLException ex){
            System.out.println("error" + ex);
            }
        
        return cuentas;
        }
    public boolean EliminarCuenta(String dni){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:ifp.db");
            PreparedStatement stm = conexion.prepareStatement("delete from cuenta where dni = ? ");
            
            stm.setString(1,dni);

            stm.executeUpdate();
            stm.close();
            conexion.close();
                    
            return true;
            } 
        catch (ClassNotFoundException ex) {
            return false;
            } 
        catch (SQLException ex) {
            return false;
            }
    }
    public boolean BuscarCuenta(String dni){
        
        for (Cuenta c : ListaCuentasBanco){
            if (c.getDni().equals(dni)){
                return true;
                }
            }
        return false;
    }
    public Cuenta CrearCuenta(String dni,String nombre){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:ifp.db");
            PreparedStatement stm = conexion.prepareStatement("INSERT INTO cuenta (dni,nombre,fecha) VALUES (?,?,?)");
            
            Cuenta c = new Cuenta(dni,nombre);
            ListaCuentasBanco.add(c);
            LocalDate fechaLD = c.getDate(); 
            java.sql.Date fechaSQL =  java.sql.Date.valueOf(fechaLD);
            
            stm.setString(1,dni);
            stm.setString(2,nombre);
            stm.setDate(3,fechaSQL );

            stm.executeUpdate();
            
            stm.close();
            conexion.close();
            
            return c;
            } 
        catch (ClassNotFoundException ex) {
            System.out.println(ex);
            return null;
            } 
        catch (SQLException ex) {
            System.out.println("Error Dni ("+dni+") existente --->  "+ex);
            return null;
            }
    }
    public boolean CambiarNombre(String dni,String nombreNuevo){
        try{
            
            Class.forName("org.sqlite.JDBC");
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:ifp.db");
            PreparedStatement stm = conexion.prepareStatement("update cuenta set nombre = '"
                + nombreNuevo + "' where dni = '" + dni + "'");
            
            stm.executeUpdate();
            stm.close();
            conexion.close();

            return true;
            }   
        catch (ClassNotFoundException ex) {
            return false;
            } 
        catch (SQLException ex) {
            return false;
            }
    }
   
        
        
         
            
        
}
