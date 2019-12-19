package top.itcat.diagnose;

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
import top.itcat.rpc.client.HospitalServiceClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiagnoseApiTest2 {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws InterruptedException, IOException, KeeperException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetApply() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnose/apply/get")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testAddApply() throws Exception {
        Apply entity = new Apply();
        JSONObject req = new JSONObject();
        entity.setMedicalRecordNo(1L);
        entity.setTime(1L);
        entity.setCategory(1);
        entity.setDoctorId(1L);
        entity.setId(1L);

        ApplyItem entity1 = new ApplyItem();
        entity1.setId(1L);
        entity1.setApplyId(1L);
        entity1.setChargeItemId(1L);
        entity1.setMedicalDoctorId(1L);
        entity1.setNote("a");
        entity1.setResult("a");
        entity1.setStatus(1);

        ArrayList<ApplyItem> items = new ArrayList<>();
        items.add(entity1);
        entity.setItems(items);

        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("applys", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/apply/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateApply() throws Exception {
        Apply entity = new Apply();
        JSONObject req = new JSONObject();
        entity.setMedicalRecordNo(1L);
        entity.setTime(1L);
        entity.setCategory(1);
        entity.setDoctorId(1L);
        entity.setId(1L);

        ApplyItem entity1 = new ApplyItem();
        entity1.setId(1L);
        entity1.setApplyId(1L);
        entity1.setChargeItemId(1L);
        entity1.setMedicalDoctorId(1L);
        entity1.setNote("a");
        entity1.setResult("a");
        entity1.setStatus(1);

        ArrayList<ApplyItem> items = new ArrayList<>();
        items.add(entity1);
        entity.setItems(items);

        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("apply", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/apply/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testGetMedicalRecord() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnose/medical/record/get")
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
    public void testAddMedicalRecord() throws Exception {
        MedicalRecord entity = new MedicalRecord();
        JSONObject req = new JSONObject();
        entity.setId(8L);
        entity.setMedicalRecordNo(1L);
        entity.setDoctorId(1L);
        entity.setTime(1L);
        entity.setComplain("a");
        entity.setCurrentMedicalHistory("a");
        entity.setCurrentMedicalTreatment("a");
        entity.setAllergyHistory("a");
        entity.setPastMedicalHistory("a");
        entity.setPhysicalCheckUp("a");
        entity.setPreliminaryDiagnosisWestern("a");
        entity.setPreliminaryDiagnosisChinese("a");
        entity.setStatus(1);

        DoctorDiagnostic entity1 = new DoctorDiagnostic();
        entity1.setId(26L);
        entity1.setDoctorId(1L);
        entity1.setDiagnosticId(1L);
        entity1.setMedicalRecordNo(1L);
        entity1.setCatalog(1);
        entity1.setMain(1);
        entity1.setSuspect(1);
        entity.setList(Arrays.asList(new DoctorDiagnostic[]{entity1}));

        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("bean", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/medical/record/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        MedicalRecord entity = new MedicalRecord();
        JSONObject req = new JSONObject();
        entity.setId(8L);
        entity.setMedicalRecordNo(1L);
        entity.setDoctorId(1L);
        entity.setTime(1L);
        entity.setComplain("a");
        entity.setCurrentMedicalHistory("a");
        entity.setCurrentMedicalTreatment("a");
        entity.setAllergyHistory("a");
        entity.setPastMedicalHistory("a");
        entity.setPhysicalCheckUp("a");
        entity.setPreliminaryDiagnosisWestern("a");
        entity.setPreliminaryDiagnosisChinese("a");
        entity.setStatus(1);

        DoctorDiagnostic entity1 = new DoctorDiagnostic();
        entity1.setId(26L);
        entity1.setDoctorId(1L);
        entity1.setDiagnosticId(1L);
        entity1.setMedicalRecordNo(1L);
        entity1.setCatalog(1);
        entity1.setMain(1);
        entity1.setSuspect(1);
        entity.setList(Arrays.asList(new DoctorDiagnostic[]{entity1}));

        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("medical_record", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/medical/record/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }
}
