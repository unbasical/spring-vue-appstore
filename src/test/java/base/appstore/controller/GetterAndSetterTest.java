package base.appstore.controller;

import base.appstore.controller.dto.*;
import base.appstore.model.*;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.EqualsAndHashCodeMatchRule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GetterAndSetterTest {

    private final Class classToTest;

    public GetterAndSetterTest(Class classToTest) {
        this.classToTest = classToTest;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {AppDto.class},
                {AuthenticationRequest.class},
                {CommentDto.class},
                {JWTTokenResponse.class},
                {RatingDto.class},
                {ScreenshotDto.class},
                {TagDto.class},
                {UploadFileResponse.class},
                {UserDto.class},
                {App.class},
                {Comment.class},
                {Logo.class},
                {Rating.class},
                {Screenshot.class},
                {Tag.class},
                {User.class},
                {Feedback.class},
                {FeedbackDto.class}
        });
    }



    @Test
    public void validateSettersAndGetters() {
        PojoClass activityPojo = PojoClassFactory.getPojoClass(classToTest);

        Validator validator = ValidatorBuilder.create()
                // Lets make sure that we have a getter and a setter for every field defined.
                .with(new SetterMustExistRule()).with(new GetterMustExistRule())

                // Lets also validate that they are behaving as expected
                .with(new SetterTester()).with(new GetterTester())

                // Hashcode and equals
                .with(new EqualsAndHashCodeMatchRule())
                .build();

        // Start the Test
        validator.validate(activityPojo);
    }


}
