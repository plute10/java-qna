package codesquad.web;

import codesquad.domain.AnswerRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.helper.HtmlFormDataBuilder;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AnswerAcceptanceTest extends AcceptanceTest {
    private HtmlFormDataBuilder htmlFormDataBuilder;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    public void addAnswer() {
        HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.urlEncodedForm()
                                                                               .addParameter("contents", "hello~")
                                                                               .build();

        ResponseEntity<String> response = basicAuthTemplate(defaultUser()).postForEntity("/questions/1/answers", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateAnswer() {
        HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.urlEncodedForm()
                                                                               .addParameter("contents", "hahahahaha")
                                                                               .build();

        basicAuthTemplate(defaultUser()).put("/questions/1/answers/1/form", request, String.class);
        assertThat(answerRepository.findOne(1L).getContents(), is("hahahahaha"));
    }

    @Test
    public void delete() {
        basicAuthTemplate().delete("/questions/1/answers/1");
        assertThat(answerRepository.findOne(1L).isDeleted(), is(true));
    }
}
