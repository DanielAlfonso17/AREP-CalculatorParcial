package apirest;

import factory.OperationsFactory;
import org.json.JSONObject;
import services.Operations;
import services.Seno;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainApp {
    private static double respuesta;
    private static String operacion;
    private static JSONObject respuestaJson;

    public static void main(String args[]){
        port(getPort());
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "/formulario");
        });

        post("/",((req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String value = req.body().substring(7);
            String[] list = value.split("%2C");
            OperationsFactory factory = new OperationsFactory(value);
            Operations ope = factory.getOperacion();
            System.out.println();
            operacion = ope.getClass().getName().substring(9);
            respuesta = ope.getResult((Double.parseDouble(list[0])));
            respuestaJson = new JSONObject();
            respuestaJson.put("operacion",operacion);
            respuestaJson.put("resultado",respuesta);
            respuestaJson.put("valor",list[0]);
            System.out.println(respuestaJson);
            res.redirect("/resultado");
            return new ModelAndView(model,"/resultado");
        }),new ThymeleafTemplateEngine());

        get("/resultado", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("operacion",operacion);
            model.put("resultado", respuesta);
            return render(model, "/resultado");
        });

        post("/resultado", (req,res) -> {
            Map<String, Object> model = new HashMap<>();
            res.redirect("/");
            return new ModelAndView(model, "/");
        });


    }

    public static String render(Map<String, Object> model, String templatePath) {
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    public static JSONObject getJSON(){
        return respuestaJson;
    }


}
