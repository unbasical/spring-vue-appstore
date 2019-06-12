package base.appstore.controller.dto;

import base.appstore.model.Feedback;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackDto {

    private String subject;
    private String content;
    private long id;

    public FeedbackDto(Feedback feedback) {
        this.id = feedback.getId();
        this.subject = feedback.getSubject();
        this.content = feedback.getContent();
    }

    public Feedback toEntity() {
        final Feedback feedback = new Feedback();
        feedback.setSubject(subject);
        feedback.setContent(content);
        return feedback;
    }
}
