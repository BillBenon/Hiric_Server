package controllers.groupmessaging;

/**
 * @author : DABAGIRE Valens
 * @description : Handling group messaging actions
 */

import com.fasterxml.jackson.databind.JsonNode;

public class GroupControllers {

    public String mainMethod(JsonNode request) throws Exception{
        String action = request.get("action").asText();
        String response = "";

        switch (action){
            case "createGroup" -> {
                response = new GroupActions().createGroup(request);
            }
            case "joinGroup" ->{
                response = new GroupActions().joinGroup(request);
            }
            case "deleteGroup" ->{
                response = new GroupActions().deleteGroup(request);
            }
            case "leaveGroup" ->{
                response = new GroupActions().leaveGroup(request);
            }
            default -> {
                response = "Something went wrong";
                return response;
            }
        }

        return response;
    }

}
