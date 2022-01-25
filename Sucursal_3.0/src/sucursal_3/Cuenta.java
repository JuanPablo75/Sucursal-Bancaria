
package sucursal_3;
import java.time.LocalDate;
public class Cuenta {

    private LocalDate fecha_de_creacion;
    private String nombre;
    private String numero_de_cuenta;
    private Float saldo = 0f;
    
    public Cuenta (String DNI,String nombre){
        
        fecha_de_creacion =  LocalDate.now();
        this.nombre = nombre;
        this.numero_de_cuenta = DNI;
        }
    public LocalDate getDate(){
        return fecha_de_creacion;
    }
    
    public boolean setFecha (LocalDate fecha){
        fecha_de_creacion = fecha ;
        return true;
    }
    public boolean Extraccion(Float cantidad){
        if(saldo > cantidad){
            return true;
        }
        return false;
    }
    public String getNombre(){
        return nombre;
        }
    public boolean setSaldo(float saldo){
        this.saldo = saldo;
        return true;
    }
    public String getDni(){
        return numero_de_cuenta;
        }
    
    public Float getSaldo(){
        //System.out.println("Su saldo es de: " + saldo + " €");
        return saldo;
        }
}


