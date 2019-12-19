package top.itcat.api.diagnose;

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
import top.itcat.entity.diagnose.*;
import top.itcat.entity.registration.Registration;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiagnoseApiTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws InterruptedException, IOException, KeeperException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetDiagnostic() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnostic/get")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddDiagnostic() throws Exception {
        Diagnostic entity = new Diagnostic();
        JSONObject req = new JSONObject();
        entity.setDirectoryId(1L);
        entity.setCode("123");
        entity.setName("asd");
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("list", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnostic/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateDiagnostic() throws Exception {
        Diagnostic entity = new Diagnostic();
        JSONObject req = new JSONObject();
        entity.setDirectoryId(1L);
        entity.setCode("123");
        entity.setName("asd");
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("diagnostic", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnostic/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetFirstDiagDir() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnostic/directory/first/get")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddFirstDiagDir() throws Exception {
        FirstDiagDir entity = new FirstDiagDir();
        JSONObject req = new JSONObject();
        entity.setName("asd");
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("list", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnostic/directory/first/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateFirstDiagDir() throws Exception {
        FirstDiagDir entity = new FirstDiagDir();
        JSONObject req = new JSONObject();
        entity.setName("asd");
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("diagnostic_directory", entityJson);
        System.out.println(req);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnostic/directory/first/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetSecondDiagDir() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnostic/directory/second/get")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddSecondDiagDir() throws Exception {
        SecondDiagDir entity = new SecondDiagDir();
        JSONObject req = new JSONObject();
        entity.setFirstDiagDirId(1L);
        entity.setName("asd");
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("list", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnostic/directory/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateSecondDiagDir() throws Exception {
        SecondDiagDir entity = new SecondDiagDir();
        JSONObject req = new JSONObject();
        entity.setFirstDiagDirId(1L);
        entity.setName("asd");
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("diagnostic_directory", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnostic/directory/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetRegistration() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/registration/get")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddRegistration() throws Exception {
        Registration entity = new Registration();
        JSONObject req = new JSONObject();
        entity.setBillingCategoryId(1L);
        entity.setRegistrationSource(1);
        entity.setRegistrationTime(1L);
        entity.setExpense(123.0);
        entity.setIdentityCardNo("asd");
        entity.setMedicalRecordNo(1L);
        entity.setSchedulePlanId(1L);
        entity.setSeeDoctorTime(1L);
        entity.setSequenceNumber(1L);
        entity.setStatus(1);
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("registrations", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/registration/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateRegistration() throws Exception {
        Registration entity = new Registration();
        JSONObject req = new JSONObject();
        entity.setBillingCategoryId(1L);
        entity.setRegistrationSource(1);
        entity.setRegistrationTime(1L);
        entity.setExpense(123.0);
        entity.setIdentityCardNo("asd");
        entity.setMedicalRecordNo(1L);
        entity.setSchedulePlanId(1L);
        entity.setSeeDoctorTime(1L);
        entity.setSequenceNumber(1L);
        entity.setStatus(1);
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("registration", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/registration/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetSchedulePlan() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnose/schedule/plan/get")
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
    public void testAddSchedulePlan() throws Exception {
        SchedulePlan entity = new SchedulePlan();
        JSONObject req = new JSONObject();
        entity.setEndTime(1L);
        entity.setRemain(1);
        entity.setScheduleId(1L);
        entity.setStartTime(1L);
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("list", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/schedule/plan/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateSchedulePlan() throws Exception {
        SchedulePlan entity = new SchedulePlan();
        JSONObject req = new JSONObject();
        entity.setEndTime(1L);
        entity.setRemain(1);
        entity.setScheduleId(1L);
        entity.setStartTime(1L);
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("plan", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/schedule/plan/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetScheduleRule() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnose/schedule/rule/get")
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
    public void testAddScheduleRule() throws Exception {
        ScheduleRule entity = new ScheduleRule();
        JSONObject req = new JSONObject();
        entity.setDepartmentId(1L);
        entity.setDay(1);
        entity.setDoctorId(1L);
        entity.setLimitNumber(1);
        entity.setEndTime(1L);
        entity.setNoonBreak(1);
        entity.setOperationDate(1);
        entity.setRegistrationLevelId(1L);
        entity.setStartTime(1L);
        entity.setId(7L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("schedule_rules", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/schedule/rule/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateScheduleRule() throws Exception {
        ScheduleRule entity = new ScheduleRule();
        JSONObject req = new JSONObject();
        entity.setDepartmentId(1L);
        entity.setDay(1);
        entity.setDoctorId(1L);
        entity.setLimitNumber(1);
        entity.setEndTime(1L);
        entity.setNoonBreak(1);
        entity.setOperationDate(1);
        entity.setRegistrationLevelId(1L);
        entity.setStartTime(1L);
        entity.setId(7L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("schedule_rule", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/schedule/rule/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }
}