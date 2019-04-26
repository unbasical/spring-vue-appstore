package base.appstore.controller.dto;

import base.appstore.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String text;
    private Date createDate;
    private UserDto author;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.createDate = comment.getCreateDate();
        this.author = new UserDto(comment.getAuthor());
    }

    public Comment toEntity() {
        final Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(author.toEntity());
        return comment;
    }
}
