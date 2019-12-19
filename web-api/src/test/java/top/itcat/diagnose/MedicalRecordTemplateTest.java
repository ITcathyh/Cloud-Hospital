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
public class MedicalRecordTemplateTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws InterruptedException, IOException, KeeperException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetMedicalRecordTemplate() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnose/medical/template/get")
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
    public void testAddMedicalRecordTemplate() throws Exception {
        JSONObject req = new JSONObject();

        MedicalRecordTemplate entity = new MedicalRecordTemplate();
        entity.setId(8L);
        entity.setCode("a");
        entity.setName("a");
        entity.setDoctorId(1L);
        entity.setSuitableRange(1);
        entity.setComplain("a");
//        entity.setAllergyHistory("a");
        entity.setCurrentMedicalHistory("a");
        entity.setPhysicalCheckUp("a");
        entity.setPreliminaryDiagnosisWestern("a");
        entity.setPreliminaryDiagnosisChinese("a");

        DiagnosticForMedicalRecordTemplate entity1 = new DiagnosticForMedicalRecordTemplate();
        entity1.setId(42L);
        entity1.setDoctorId(1L);
        entity1.setDiagnosticId(1L);
//        entity1.setMedicalRecordTemplateId(1L);
        entity1.setCatalog(1);
        ArrayList<DiagnosticForMedicalRecordTemplate> items = new ArrayList<>();
        items.add(entity1);
        entity.setDoctorDiagnostics(items);


        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("bean", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/medical/template/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateMedicalRecordTemplate() throws Exception {
        JSONObject req = new JSONObject();

        MedicalRecordTemplate entity = new MedicalRecordTemplate();
        entity.setId(8L);
        entity.setCode("a");
        entity.setName("a");
        entity.setDoctorId(1L);
        entity.setSuitableRange(1);
        entity.setComplain("a");
        entity.setCurrentMedicalHistory("a");
        //        entity.setAllergyHistory("a");
        entity.setPhysicalCheckUp("a");
        entity.setPreliminaryDiagnosisWestern("a");
        entity.setPreliminaryDiagnosisChinese("a");

        DiagnosticForMedicalRecordTemplate entity1 = new DiagnosticForMedicalRecordTemplate();
        entity1.setId(42L);
        entity1.setDoctorId(1L);
        entity1.setDiagnosticId(1L);
        entity1.setMedicalRecordTemplateId(1L);
        entity1.setCatalog(1);
        ArrayList<DiagnosticForMedicalRecordTemplate> items = new ArrayList<>();
        items.add(entity1);
        entity.setDoctorDiagnostics(items);

        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("medical_record_template", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/medical/template/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }
}
