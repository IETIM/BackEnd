package edu.eci.ieti.ProjectIeti.Exceptions;

public class ExceptionTienda extends Exception {
    public static final String TIENDA_NO_ENCONTRADA = "La tienda no se encuentra registrada en el sistema" ;
    public static final String PRODUCTO_NO_ENCONTRADO= "El producto no se encuentra registrado";

    public ExceptionTienda(){
        super();
    }
    public ExceptionTienda(String message){
        super(message);
    }
}
