
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyServlet{

    private boolean isDigit(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    private String headersFormation(boolean isOk, int contentLength){
        String headers = "";
        if (isOk)
            headers = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "Connection: close\r\n\r\n";
        else headers = "HTTP/1.1 404 Not Found\r\n"+
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + contentLength + "\r\n" +
                "Connection: close\r\n\r\n";

        return headers;
    }

    private User readFromTheUserFile(File userFile, User user) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(userFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        user = (User)ois.readObject();
        return user;
    }

    private void writeToUserFile(File userFile, User user) throws IOException {
        FileOutputStream fos = new FileOutputStream(userFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(user);
        oos.flush();
        oos.close();
    }

    private String takeID(String fileName){
        return fileName.substring(2, fileName.indexOf("}"));
    }

    private boolean checkСorrectnessCreateRequest(String request){
        if (request.contains("/user/create?"))
            return true;

        return false;
    }

    private boolean checkСorrectnessDeleteRequest(String request){
        if (request.contains("/user/delete/"))
            return true;

        return false;
    }

    private boolean checkСorrectnessListRequest(String request){
            return request.equals("/user/list");
    }

    private boolean checkСorrectnessIDRequest(String request){
        String ID_Str = "";
        boolean bool1 = request.startsWith("/user/");
        try {
            ID_Str = request.split("/user/")[1];
        }
        catch (Exception e){
            return false;
        }
        boolean bool2 = request.endsWith(ID_Str);
        boolean bool3 = isDigit(ID_Str);
        boolean bool4 = bool1&&bool2&&bool3;
        return bool4;
    }

    private boolean setByParametersNames(String request, User user){
        try {
            String parametersLine = request.split("/user/create")[1];
            String[] p = parametersLine.split("&");
            if (p.length == 3) {
                String[] pp1 = p[0].split("=");
                String[] pp2 = p[1].split("=");
                String[] pp3 = p[2].split("=");
                String p1 = pp1[0];
                String p2 = pp2[0];
                String p3 = pp3[0];
                String value1 = pp1[1];
                String value2 = pp2[1];
                String value3 = pp3[1];

                if (p1.equals("?name") && p2.equals("age") && p3.equals("salary")) {
                    user.setName(value1);
                    user.setAge(Integer.parseInt(value2));
                    user.setSalary(Long.parseLong(value3));
                    return true;
                }

                if (p1.equals("?name") && p2.equals("salary") && p3.equals("age")) {
                    user.setName(value1);
                    user.setSalary(Long.parseLong(value2));
                    user.setAge(Integer.parseInt(value3));
                    return true;
                }
                if (p1.equals("?age") && p2.equals("name") && p3.equals("salary")) {
                    user.setAge(Integer.parseInt(value1));
                    user.setName(value2);
                    user.setSalary(Long.parseLong(value3));
                    return true;
                }
                if (p1.equals("?age") && p2.equals("salary") && p3.equals("name")) {
                    user.setAge(Integer.parseInt(value1));
                    user.setSalary(Long.parseLong(value2));
                    user.setName(value3);
                    return true;
                }
                if (p1.equals("?salary") && p2.equals("name") && p3.equals("age")) {
                    user.setSalary(Long.parseLong(value1));
                    user.setName(value2);
                    user.setAge(Integer.parseInt(value3));
                    return true;
                }
                if (p1.equals("?salary") && p2.equals("age") && p3.equals("name")) {
                    user.setSalary(Long.parseLong(value1));
                    user.setAge(Integer.parseInt(value2));
                    user.setName(value3);
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Ошибочка3");
        }
        return false;
    }

    private String createUser(String request, List<Integer> freeIDs, File[] listFiles){
        int contentLength = 0;
        String headers = "";
        User user = new User();
        try {
            if (setByParametersNames(request, user)) {
                int ID = 0;
                if (!freeIDs.isEmpty()){
                    ID = freeIDs.get(0);
                    freeIDs.remove(0);
                }
            else
            ID = listFiles.length;

            contentLength = "ID:  ".length() + String.valueOf(ID).length();
            File userFile = new File(listFiles[0].getParent(), "${" + ID + "}.bin");
            userFile.createNewFile();
            writeToUserFile(userFile, user);
            headers = headersFormation(true,contentLength);
            return headers + "\nID: " + ID;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        contentLength = "404 Not Found".length();
        headers = headersFormation(false,contentLength);
        return headers + "404 Not Found";
    }

    private String deleteUser(String request, List<Integer> freeIDs, File[] listFiles){
        String headers = "";
        int contentLength = 0;
        try{
            String ID_Str = request.split("/user/delete/")[1];
            int ID = Integer.parseInt(ID_Str);
            for (File userFile: listFiles) {
                if (takeID(userFile.getName()).equals(ID_Str)){
                    userFile.delete();
                    freeIDs.add(ID);
                    contentLength = "User is deleted".length();
                    headers = headersFormation(true,contentLength);
                    return headers + "User is deleted";
                }
            }
            contentLength = "User is not found".length();
            headers = headersFormation(false,contentLength);
            return headers + "User is not found";
        }
        catch (Exception e){
            System.out.println("Ошибочка4");
        }
        contentLength = "Request is not corrected".length();
        headers = headersFormation(false,contentLength);
        return headers + "Request is not corrected";
    }

    private String listUsers(File[] listFiles){
        String headers = "";
        int contentLength = 0;
        User user = new User();
        JSONFormatterImpl jsonFormatter = new JSONFormatterImpl();
        StringBuilder allUsersJSONList = new StringBuilder();
        try {
            if (listFiles.length==1){
                contentLength = "Users do not exist".length();
                headers = headersFormation(false,contentLength);
                return headers + "Users do not exist";
            }
            for (int i = 0; i < listFiles.length-1; i++){
                user = readFromTheUserFile(listFiles[i], user);
                String JSONLine = jsonFormatter.marshall(user,0);
                allUsersJSONList.append(JSONLine).append("\n\n");
            }
            String resultLine = allUsersJSONList.toString().replace("\n","<br>");
            contentLength = resultLine.length();
            headers = headersFormation(true,contentLength);
            return headers+resultLine;
        }
        catch (Exception e){
            System.out.println("Error in the List method");
        }
        contentLength = "Server error".length();
        headers = headersFormation(false,contentLength);
        return headers+"Server error";
    }

    private String printUser(String request, List<Integer> freeIDs, File[] listFiles){
        User user = new User();
        String headers = "";
        int contentLength = 0;
        JSONFormatterImpl jsonFormatter = new JSONFormatterImpl();
        try {
            String ID_Str = request.split("/user/")[1];
            int ID = Integer.parseInt(ID_Str);
            if (freeIDs.contains(ID)){
                contentLength = "User not found".length();
                headers = headersFormation(false,contentLength);
                return headers+"User not found";
            }
            for (int i = 0; i < listFiles.length-1; i++) {
                File userFile = listFiles[i];
                if (takeID(userFile.getName()).equals(ID_Str)){
                    user = readFromTheUserFile(userFile,user);
                    String JSONLine = jsonFormatter.marshall(user,0).replace("\n","<br>");
                    contentLength = JSONLine.length();
                    headers = headersFormation(true,contentLength);
                    return headers + JSONLine;
                }
            }
            contentLength = "User not found".length();
            headers = headersFormation(false,contentLength);
            return headers+"User not found";
        }
        catch (Exception e){
            System.out.println("Error in the printUser method");
        }
        contentLength = "Server error".length();
        headers = headersFormation(false,contentLength);
        return headers+"Server error";
    }

    public static void main(String[] args) throws Throwable {
        String optionsPath = "";
        if (args.length!=0) {
        if (args[0]==""||args[0]==null) {
            optionsPath = "/Users/amayakbulgadaryan/Documents/HTTPServer/options.properties";
        }
        else optionsPath = args[0];
        }
        else optionsPath = "/Users/amayakbulgadaryan/Documents/HTTPServer/options.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(optionsPath));
        String folderPath = properties.getProperty("pathFolder");
        File[] listFiles = new File(folderPath).listFiles();
        List<Integer> freeIDs = new ArrayList<>();
        int port = 4571;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                MyServlet servlet = new MyServlet();
                try {
                    String s = servlet.readRequest(is,freeIDs,listFiles);
                    servlet.writeResponse(s,os);
                } catch (Throwable throwable){
                    System.out.println("Ошибочка");
                }
                finally {
                    try {

                    } catch (Throwable t){
                        System.out.println("Ошибочка2");
                    }
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void writeResponse(String s, OutputStream os) throws Throwable {
        String result = s;
        os.write(result.getBytes());
        os.flush();
    }

    private String readRequest(InputStream is, List freeIDs, File[] listFiles) throws Throwable {
        String headers = "";
        int contentLength = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = "";
        s = br.readLine();
        char space = ' ';
        String request = s.split(String.valueOf(space))[1];
        if (checkСorrectnessCreateRequest(request))
            return createUser(request,freeIDs,listFiles);
        if (checkСorrectnessDeleteRequest(request))
            return deleteUser(request,freeIDs,listFiles);
        if (checkСorrectnessListRequest(request))
            return listUsers(listFiles);
        if (checkСorrectnessIDRequest(request))
            return printUser(request,freeIDs,listFiles);
        if (request.equals("/")) {
            String helloLine = "Hello, this is Localhost and I identify four function/request in the URL address:<br>";
            String firstRequest = "1)/user/create?name=XXX&age=YYY&salary=ZZZ<br>";
            String secondRequest = "2)/user/delete/id<br>";
            String thirdRequest = "3)/user/list<br>";
            String fourthRequest = "4)/user/id";
            String response = helloLine + firstRequest + secondRequest + thirdRequest + fourthRequest;
            contentLength = response.length();
            headers = headersFormation(false, contentLength);
            return headers + response;
        }
        headers = headersFormation(false,"Request is not corrected".length());
        return headers+"Request is not corrected";
    }
}



