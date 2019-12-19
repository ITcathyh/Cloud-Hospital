package top.itcat.api.registration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.zookeeper.KeeperException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import top.itcat.entity.registration.RegistrationLevel;
import top.itcat.rpc.client.HospitalServiceClient;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationApiTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws InterruptedException, IOException, KeeperException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetRegistrationLevel() throws Exception {
        JSONObject req = new JSONObject();
//        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.get("/registration/get")
                .header("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPRDAwMDAwMiIsImNvZGUiOiJPRDAwMDAwMiIsInJvbGUiOjEsImRlcGFydG1lbnRJZCI6IjEiLCJkZXNjcmlwdGlvbiI6ImRyIiwiaWQiOiIxNSIsInRpdGxlIjozLCJleHAiOjE1Njk1MDU0ODUsImlhdCI6MTU2MDUwNTQ4NSwianRpIjoiY2I3N2I1OGQtOTYyZi00OThiLWIwZDMtMjAwNzI4ZmZkNzA5In0.BucRWDcfzaDF0e5F9IEL0QrDhtoNXGlEeOwNcsMTZVY")
                .header("Origin", "localhost:6788")
                .param("searchKey", "a")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddRegistrationLevel() throws Exception {
        RegistrationLevel entity = new RegistrationLevel();
        JSONObject req = new JSONObject();
//        entity.setDisplaySeqNum(1L);
        entity.setPrice(1.00);
        entity.setShowDefault(1);
        entity.setCode("CS");
        entity.setName("测试");
        entity.setId(4L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("list", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/registration/level/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateRegistrationLevel() throws Exception {
        RegistrationLevel entity = new RegistrationLevel();
        JSONObject req = new JSONObject();
//        entity.setDisplaySeqNum(1L);
        entity.setPrice(1.00);
        entity.setShowDefault(1);
        entity.setCode("CS");
        entity.setName("测试");
        entity.setId(4L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("registration_level", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/registration/level/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }
}