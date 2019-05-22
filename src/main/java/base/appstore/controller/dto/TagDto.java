package base.appstore.controller.dto;

import base.appstore.model.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagDto {

    private Long id;
    private String name;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

    public Tag toEntity() {
        final Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }
}
