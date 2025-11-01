
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import ru.Bodyaaaa.Main;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class EndpointAuthorizationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void registrationEndpointShouldBeAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/res/register"))
                .andExpect(status().isOk());
    }


}
