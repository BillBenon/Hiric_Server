package hiric.controllers.hiringcontrollers.jobpostingcontrollers;

import com.fasterxml.jackson.databind.JsonNode;


public class JobPostingControllers {
    public String mainMethod(JsonNode request) throws Exception {
        String action = request.get("action").asText();

        String response = "";

        switch (action) {
            case "createJobPost":
                response = new JobPostingActions().createJobPost(request);
                return response;
            case "getJobPosts":
                response = new JobPostingActions().getJobPosts(request);
                return response;
        }
        return "";
    }
}
