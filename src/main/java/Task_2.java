import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;


public class Task_2 {

    @Autowired
    private RestTemplate restTemplate;

    public void test() throws IOException {
        ResponseEntity<PostDto[]> posts = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users/1/posts", PostDto[].class);

        Optional<PostDto> lastPost = Arrays.stream(posts.getBody()).sorted(Comparator.comparing(PostDto::getId).reversed()).findFirst();

        ResponseEntity<CommentDto[]> commentsDto = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/" + lastPost.get().getId() + "/comments", CommentDto[].class);

        Arrays.stream(commentsDto.getBody()).forEach(item -> System.out.println(item.getBody()));

        Path path = Paths.get("/tmp/foo/bar.txt");
        Files.createDirectories(path.getParent());
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            System.err.println("already exists: " + e.getMessage());
        }
        Arrays.stream(commentsDto.getBody()).forEach(comment -> {
            try {
                Files.write(Paths.get("/tmp/foo/bar.txt"), comment.getBody().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static class PostDto {
        private Long id;

        public PostDto() {
        }

        public PostDto(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    private static class CommentDto {
        private String body;

        public CommentDto() {
        }

        public CommentDto(String body) {
            this.body = body;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
