package fatesg.spark.api;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.halt;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import fatesg.spark.model.Cliente;
import fatesg.spark.model.Endereco;

public class ServiceApi {

	final static Logger LOGGER = Logger.getLogger(ServiceApi.class);
	final static String CONTENT_TYPE = "application/json";
	final static String TOKEN = "Bearer b9QLldmqZSVSsLfbubqR35SaTTzN8QVD";

	public static void main(String[] args) {
		clientes();
		enderecos();
	}

	public static void userApi() {

	}

	public static void clientes() {
    	
    	List<Cliente> lista = new ArrayList<Cliente>();

    	path("/api", () -> {
    		before("/*", (request, response) -> LOGGER.info("Received api call"));
    		
    		path("/cliente", () -> {  
    			
    			get("", (request, response) -> {
    				response.type(CONTENT_TYPE);
    				final String mensagem = "Acesso ao método POST";
    				LOGGER.info(mensagem);
    				response.status(200);
    				return new Gson().toJson(lista);
    			});
    			
    			post("", (request, response) -> {
    				response.type(CONTENT_TYPE);
    				Cliente cliente = new Gson().fromJson(request.body(), Cliente.class);
    				lista.add(cliente);
    				final String mensagem = "Acesso ao método POST";
    				LOGGER.info(mensagem);
    				
    				response.status(201);
    				
    				return new Gson().toJson(cliente);
    			});
                
    			put("", (request, response) -> {
    				Cliente cliente = new Gson().fromJson(request.body(), Cliente.class);
    				lista.removeIf(u -> u.getId().equals(cliente.getId()));
    				lista.add(cliente);
    				
    				final String mensagem = "Acesso ao método PUT";
    				LOGGER.info(mensagem);
    				response.status(201);
    				return new Gson().toJson(cliente);
    			});
    			
    			delete("/:id", (request, response) -> {
    				Cliente cliente = new Cliente();
    				cliente.setId(Long.parseLong(request.params(":id")));
    				
    				lista.removeIf(u -> u.getId().equals(cliente.getId()));
    				
    				final String mensagem = "Acesso ao método DELETE. Registro deletado.";
    				LOGGER.info(mensagem);
    				response.status(204);
    				return mensagem;
    			});
    		});
    	});

    }

	public static void enderecos() {
		List<Endereco> lista = new ArrayList<Endereco>();

        path("/api", () -> {
            before("/*", (request, response) -> {

                if(request.pathInfo().endsWith("/")){
                    request.pathInfo().substring(0, request.pathInfo().length() - 1);
                }

                LOGGER.info("Received api call");

                boolean authenticated = false;

                authenticated = TOKEN.equals(request.headers("Authorization"));

                if (!authenticated) {
                    halt(401, new Gson().toJson("Sessao não autorizada. Favor informar TOKEN de acesso."));
                }
            });
            
            path("/endereco/", () -> {

                get("", (request, response) -> {

                    response.type(CONTENT_TYPE);
                    final String mensagem = "Acesso ao método GET";
                    LOGGER.info(mensagem);
                    response.status(200);
                    return new Gson().toJson(lista);
                });
                                
                get("/:id", (request, response) -> {

                    response.type(CONTENT_TYPE);
                    final String mensagem = "Acesso ao método GET";
                    LOGGER.info(mensagem);
                    response.status(200);
                    return new Gson().toJson(lista.stream()
                            .filter(c -> c.getId().equals(Long.parseLong(request.params(":id"))))
                            .findAny().orElse(null));
                });

                post("", (request, response) -> {

                    //"TIPANDO" o retorno da requisição

                    response.type(CONTENT_TYPE);

                    Endereco endereco = new Gson().fromJson(request.body(), Endereco.class);
                    lista.add(endereco);

                    final String mensagem = "Acesso ao método POST";
                    LOGGER.info(mensagem);

                    response.status(201);

                    return new Gson().toJson(endereco);
                });
                
                put("", (request, response) -> {

                	Endereco endereco = new Gson().fromJson(request.body(), Endereco.class);
                    lista.removeIf(u -> u.getId().equals(endereco.getId()));
                    lista.add(endereco);

                    final String mensagem = String.format("Acesso ao método PUT. Registro atualizado %d",endereco.getId());
                    LOGGER.info(mensagem);
                    response.status(201);
                    return new Gson().toJson(endereco);
                });
               
                delete("/:id", (request, response) -> {

                    lista.removeIf(c -> c.getId().equals(Long.parseLong(request.params(":id"))));

                    final String mensagem = String.format("Acesso ao método DELETE. Registro deletado %s.",request.params(":id"));
                    LOGGER.info(mensagem);
                    response.status(204);
                    return mensagem;
                });
            });
        });
	}
}
