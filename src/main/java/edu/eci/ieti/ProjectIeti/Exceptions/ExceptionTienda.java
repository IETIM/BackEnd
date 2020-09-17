package edu.eci.ieti.ProjectIeti.Exceptions;

public class ExceptionTienda extends Exception {
    public static final String TIENDA_NO_ENCONTRADA = "La tienda no se encuentra registrada en el sistema" ;

    public ExceptionTienda(){
        super();
    }
    public ExceptionTienda(String message){
        super(message);
    }
}
