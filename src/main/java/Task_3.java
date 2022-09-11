import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class Task_3 {
    public void test2() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TodoDto[]> todos = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users/1/todos", TodoDto[].class);

        Arrays.stream(todos.getBody())
                .filter(item -> !item.isCompleted())
                .forEach(item -> System.out.println(item.getTitle()));
    }

    public static class TodoDto {
        private String title;
        private boolean completed;

        public TodoDto() {
        }

        public TodoDto(String title, boolean completed) {
            this.title = title;
            this.completed = completed;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
