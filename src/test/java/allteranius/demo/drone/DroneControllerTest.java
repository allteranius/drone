package allteranius.demo.drone;

import allteranius.demo.drone.web.DroneController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DroneControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void registered_drone_successful() throws Exception {
        mockMvc.perform(put("/drone_model").content("""
                {"name":"model1", "carryingWeight":"200"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        var result = mockMvc.perform(post("/drone").content("""
                {"name":"test", "model":"model1"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        var id = result.getResponse().getContentAsString();
        var savedModel = mockMvc.perform(get("/drone/{0}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        var json = objectMapper.readValue(savedModel.getResponse().getContentAsString(), Map.class);
        Assertions.assertEquals("test", json.get("name"));
        Assertions.assertEquals("model1", json.get("model"));
    }

    @Test
    public void registered_drone_failure() throws Exception {
        mockMvc.perform(post("/drone").content("""
                {"name":"test", "model":"none"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
    }

    @Test
    public void test_find_suited_drone() throws Exception {
        mockMvc.perform(put("/drone_model").content("""
                {"name":"model1", "carryingWeight":"200"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mockMvc.perform(put("/drone_model").content("""
                {"name":"model2", "carryingWeight":"400"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mockMvc.perform(post("/drone").content("""
                {"name":"test1", "model":"model1"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mockMvc.perform(post("/drone").content("""
                {"name":"test2", "model":"model2"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        mockMvc.perform(post("/drone").content("""
                {"name":"test3", "model":"model2"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        var result = mockMvc.perform(get("/find_drone?weight=300").content("""
                {"name":"test", "model":"model1"}
                """).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse().getContentAsString();

        TypeReference<List<Map<String, String>>> type = new TypeReference<List<Map<String, String>>>() {};
        var json = objectMapper.readValue(result, type);
        Assertions.assertEquals(2, json.size());
        Assertions.assertEquals(Sets.set("test2", "test3"), json.stream().map(map -> map.get("name")).collect(Collectors.toSet()));

    }
}
