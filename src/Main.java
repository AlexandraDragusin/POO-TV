import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import main.Output;
import main.Start;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(args[0]), Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        Output.setError(null);
        Output.setAddOutput(false);
        Start.setCurrentPage("Homepage unauthenticated");
        Start.setCurrentUser(null);

        Start.getInstance().doStart(input, output);

        for (Action action : input.getActions()) {
            Start.getInstance().executeCommand(action);
        }

        Start.getInstance().executeRecommendation();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
