package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.Log;
import controller.MainController;
import model.InputModel;

/**
 * Servlet implementation class Main
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            // ヘッダ部分の値を格納

            String head = "ACCEPT: " + request.getHeader("ACCEPT") + "\n" + "REFERER: " + request.getHeader("REFERER") + "\n" + "ACCEPT-LANGUAGE: " + request.getHeader("ACCEPT-LANGUAGE") + "\n" + "USER-AGENT: " + request.getHeader("USER-AGENT") + "\n" + "HOST: " + request.getHeader("HOST") + "\n" + "CONTENT-TYPE: " + request.getHeader("CONTENT-TYPE") + "\n" + "CONTENT-LENGTH: " + request.getHeader("CONTENT-LENGTH") + "\n" + "CONNECTION: " + request.getHeader("CONNECTION") + "\n" + "CACHE-CONTROL: " + request.getHeader("CACHE-CONTROL");

            // ボディー部分のJSON文字列を格納
            BufferedReader bufferReaderBody = new BufferedReader(request.getReader());
            String body = bufferReaderBody.readLine();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(body);

            InputModel input = new InputModel();
            input.setLineId(rootNode.get("messages").get(0).get("userId").asText());
            input.setInputMessage(rootNode.get("messages").get(0).get("text").asText());
            input.setType("text");

            String returnMsg = MainController.mainLogic(input);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            PrintWriter out = response.getWriter();

            /*新コード*/

            // JsonFactoryの生成
            JsonFactory jsonFactory = new JsonFactory();
            // JsonGeneratorの取得
            JsonGenerator generator = jsonFactory.createGenerator(out);

            // オブジェクトの書き込み
            generator.writeStartObject();
            generator.writeStringField("message", returnMsg);
            generator.writeEndObject();

            // ストリームへの書き出し
            generator.flush();

            return;
        } catch (Exception e) {
        	Log.severe(e.toString());

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            JsonFactory jsonFactory = new JsonFactory();
            JsonGenerator generator = jsonFactory.createGenerator(out);
            generator.writeStartObject();
            generator.writeStringField("message", "すいませんエラーが発生しちゃいました。\n\r仕方ないやんアーリーverだし。\n\rスクショとともに、@yamaguppy31にDMください。100円あげます。");
            generator.writeEndObject();

            // ストリームへの書き出し
            generator.flush();
        }

    }

}