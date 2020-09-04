package factory;

import services.Coseno;
import services.Operations;
import services.Seno;
import services.Tangente;

public class OperationsFactory {
    Operations operacion;

    public OperationsFactory(String ope){
        if(ope.contains("cos")){
            operacion = new Coseno();
        }else if(ope.contains("sen")){
            operacion = new Seno();
        }else if(ope.contains("tan")){
            operacion = new Tangente();
        }
    }

    public Operations getOperacion(){
        return this.operacion;
    }
}
