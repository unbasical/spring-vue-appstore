package base.appstore.repository;

import base.appstore.model.Comment;
import base.appstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findAllByAuthor(User author);

}
