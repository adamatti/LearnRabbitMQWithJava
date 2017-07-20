package adamatti.helper

import static adamatti.helper.JsonHelper.*
import spark.Request
import spark.Response
import spark.Spark

abstract class SparkHelper {
    static void start(Integer port = 8080){
        Spark.port(port)

        File webapp = new File("src/main/webapp")
        //Spark.staticFileLocation(webapp.path)
        Spark.externalStaticFileLocation(webapp.path)

        Spark.get("/healthCheck"){Request req, Response res ->
            res.header("Content-Type","application/json")

            toJsonString ([
                status: "ok",
                dt : new Date()
            ])
        }
    }
}
