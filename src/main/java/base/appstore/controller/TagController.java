package base.appstore.controller;

import base.appstore.model.Tag;
import base.appstore.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;


@RestController
@RequestMapping("/tags")
@CrossOrigin
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping()
    public Stream<String> getAllTags() {
        return tagRepository.findAll().stream().map(Tag::getName).distinct();
    }

}
