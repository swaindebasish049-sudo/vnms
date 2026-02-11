package vcti.nms.vnms.model;

import java.util.List;

public class OpenAIResponse {

    private String id;
    private String object = "chat.completion";
    private long created = System.currentTimeMillis() / 1000;
    private String model = "vnms-agent";
    private List<Choice> choices;

    public OpenAIResponse(String content) {
        this.choices = List.of(new Choice(content));
    }

    public static class Choice {
        private int index = 0;
        private Message message;
        private String finish_reason = "stop";

        public Choice(String content) {
            this.message = new Message(content);
        }

        public static class Message {
            private String role = "assistant";
            private String content;

            public Message(String content) {
                this.content = content;
            }

            public String getRole() { return role; }
            public String getContent() { return content; }
        }

        public int getIndex() { return index; }
        public Message getMessage() { return message; }
        public String getFinish_reason() { return finish_reason; }
    }

    public List<Choice> getChoices() { return choices; }
}
