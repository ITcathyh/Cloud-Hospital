package top.itcat.api.user;

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
import top.itcat.bean.user.AddAccountClerksReq;
import top.itcat.entity.user.*;
import top.itcat.rpc.client.HospitalServiceClient;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApiTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

    @Before
    public void setUp() throws InterruptedException, IOException, KeeperException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetAccountClerk() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.get("/accountclerk/get")
                .header("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPRDAwMDAwMiIsImNvZGUiOiJPRDAwMDAwMiIsInJvbGUiOjEsImRlcGFydG1lbnRJZCI6IjEiLCJkZXNjcmlwdGlvbiI6ImRyIiwiaWQiOiIxNSIsInRpdGxlIjozLCJleHAiOjE1Njk1MDU0ODUsImlhdCI6MTU2MDUwNTQ4NSwianRpIjoiY2I3N2I1OGQtOTYyZi00OThiLWIwZDMtMjAwNzI4ZmZkNzA5In0.BucRWDcfzaDF0e5F9IEL0QrDhtoNXGlEeOwNcsMTZVY")
                .header("Origin", "localhost:6788")
                .param("searchKey", "a")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddAccountClerk() throws Exception {
        AccountClerk entity = new AccountClerk();
        JSONObject req = new JSONObject();
        entity.setId(11L);
        entity.setDepartmentId(1L);
        entity.setCode("123");
        entity.setPassword("asd");
        entity.setRealname("!!");
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("account_clerks", jsonArray);


        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/accountclerk/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateAccountClerk() throws Exception {
        AccountClerk entity = new AccountClerk();
        JSONObject req = new JSONObject();
        entity.setId(11L);
        entity.setDepartmentId(1L);
        entity.setCode("大哥大！！");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("account_clerk", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/accountclerk/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetDoctor() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.get("/accountclerk/get")
                .header("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPRDAwMDAwMiIsImNvZGUiOiJPRDAwMDAwMiIsInJvbGUiOjEsImRlcGFydG1lbnRJZCI6IjEiLCJkZXNjcmlwdGlvbiI6ImRyIiwiaWQiOiIxNSIsInRpdGxlIjozLCJleHAiOjE1Njk1MDU0ODUsImlhdCI6MTU2MDUwNTQ4NSwianRpIjoiY2I3N2I1OGQtOTYyZi00OThiLWIwZDMtMjAwNzI4ZmZkNzA5In0.BucRWDcfzaDF0e5F9IEL0QrDhtoNXGlEeOwNcsMTZVY")
                .header("Origin", "localhost:6788")
                .param("searchKey", "a")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddOutpatientDoctor() throws Exception {
        OutpatientDoctor entity = new OutpatientDoctor();
        JSONObject req = new JSONObject();
        entity.setId(16L);
        entity.setInScheduling(1);
        entity.setDescription("zxc");
        entity.setJobTitle(1);
        entity.setDepartmentId(1L);
        entity.setCode("123");
        entity.setPassword("asd");
        entity.setRealname("!!");
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("doctors", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/doctor/outpatient/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateOutpatientDoctor() throws Exception {
        OutpatientDoctor entity = new OutpatientDoctor();
        JSONObject req = new JSONObject();
        entity.setInScheduling(1);
        entity.setDescription("zxc");
        entity.setJobTitle(1);
        entity.setDepartmentId(1L);
        entity.setCode("大哥大！！");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(16L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("doctor", entityJson);
        System.out.println(req);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/doctor/outpatient/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetHospitalManager() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/hospitalmanager/get")
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
    public void testAddHospitalManager() throws Exception {
        HospitalManager entity = new HospitalManager();
        JSONObject req = new JSONObject();
        entity.setDepartmentId(1L);
        entity.setCode("123");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(15L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("hospital_managers", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/hospitalmanager/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateHospitalManager() throws Exception {
        HospitalManager entity = new HospitalManager();
        JSONObject req = new JSONObject();
        entity.setDepartmentId(1L);
        entity.setCode("大哥大！！");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(15L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("hospital_manager", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/hospitalmanager/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetMedicalDoctor() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/medicaldoctor/get")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddMedicalDoctor() throws Exception {
        MedicalDoctor entity = new MedicalDoctor();
        JSONObject req = new JSONObject();
        entity.setDescription("zxc");
        entity.setJobTitle(1);
        entity.setDepartmentId(1L);
        entity.setCode("123");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(11L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("doctors", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/medicaldoctor/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateMedicalDoctor() throws Exception {
        MedicalDoctor entity = new MedicalDoctor();
        JSONObject req = new JSONObject();
        entity.setDescription("zxc");
        entity.setJobTitle(1);
        entity.setDepartmentId(1L);
        entity.setCode("大哥大！！");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(11L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("doctor", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/medicaldoctor/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetPharmacyManager() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/pharmacymanager/get")
                .header("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPRDAwMDAwMiIsImNvZGUiOiJPRDAwMDAwMiIsInJvbGUiOjEsImRlcGFydG1lbnRJZCI6IjEiLCJkZXNjcmlwdGlvbiI6ImRyIiwiaWQiOiIxNSIsInRpdGxlIjozLCJleHAiOjE1Njk1MDU0ODUsImlhdCI6MTU2MDUwNTQ4NSwianRpIjoiY2I3N2I1OGQtOTYyZi00OThiLWIwZDMtMjAwNzI4ZmZkNzA5In0.BucRWDcfzaDF0e5F9IEL0QrDhtoNXGlEeOwNcsMTZVY")
                .header("Origin", "localhost:6788")
                .param("searchKey", "a")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddPharmacyManager() throws Exception {
        PharmacyManager entity = new PharmacyManager();
        JSONObject req = new JSONObject();
        entity.setDepartmentId(1L);
        entity.setCode("123");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(17L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("pharmacy_managers", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/pharmacymanager/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdatePharmacyManager() throws Exception {
        PharmacyManager entity = new PharmacyManager();
        JSONObject req = new JSONObject();
        entity.setDepartmentId(1L);
        entity.setCode("大哥大！！");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(17L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("pharmacy_manager", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/pharmacymanager/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetTollCollector() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/tollcollector/get")
                .header("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPRDAwMDAwMiIsImNvZGUiOiJPRDAwMDAwMiIsInJvbGUiOjEsImRlcGFydG1lbnRJZCI6IjEiLCJkZXNjcmlwdGlvbiI6ImRyIiwiaWQiOiIxNSIsInRpdGxlIjozLCJleHAiOjE1Njk1MDU0ODUsImlhdCI6MTU2MDUwNTQ4NSwianRpIjoiY2I3N2I1OGQtOTYyZi00OThiLWIwZDMtMjAwNzI4ZmZkNzA5In0.BucRWDcfzaDF0e5F9IEL0QrDhtoNXGlEeOwNcsMTZVY")
                .header("Origin", "localhost:6788")
                .param("searchKey", "a").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddTollCollector() throws Exception {
        TollCollector entity = new TollCollector();
        JSONObject req = new JSONObject();
        entity.setDepartmentId(1L);
        entity.setCode("123");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(12L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("toll_collectors", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/tollcollector/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateTollCollector() throws Exception {
        TollCollector entity = new TollCollector();
        JSONObject req = new JSONObject();
        entity.setDepartmentId(1L);
        entity.setCode("大哥大！！");
        entity.setPassword("asd");
        entity.setRealname("!!");
        entity.setId(12L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("toll_collector", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/tollcollector/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }
}