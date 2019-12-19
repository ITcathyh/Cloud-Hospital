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
import top.itcat.entity.medical.PrescriptionHerb;
import top.itcat.entity.medical.PrescriptionHerbItem;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrescriptionHerbTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws InterruptedException, IOException, KeeperException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetPrescriptionHerb() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnose/prescription/herb/get")
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
    public void testAddPrescriptionHerb() throws Exception {
        JSONObject req = new JSONObject();

        PrescriptionHerb entity = new PrescriptionHerb();
        entity.setId(1L);
        entity.setMedicalRecordNo(1L);
        entity.setType(1);
        entity.setNumber(1);
        entity.setFrequency(1);
        entity.setUsage(1);
        entity.setDoctorId(1L);
        entity.setTreatment("a");
        entity.setTreatmentDetail("a");
        entity.setAdvice("a");
        entity.setPrice(1.0);
        entity.setStatus(1);

        PrescriptionHerbItem entity1 = new PrescriptionHerbItem();
        entity1.setId(1L);
        entity1.setPrescriptionId(1L);
        entity1.setItemId(1L);
        entity1.setChargeItemId(1L);
        entity1.setMedicalDoctorId(1L);
        entity1.setPrice(1.0);

        ArrayList<PrescriptionHerbItem> items = new ArrayList<>();
        items.add(entity1);
        entity.setItems(items);

        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("list", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/prescription/herb/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdatePrescriptionHerb() throws Exception {
        JSONObject req = new JSONObject();

        PrescriptionHerb entity = new PrescriptionHerb();
        entity.setId(1L);
        entity.setMedicalRecordNo(1L);
        entity.setType(1);
        entity.setNumber(1);
        entity.setFrequency(1);
        entity.setUsage(1);
        entity.setDoctorId(1L);
        entity.setTreatment("a");
        entity.setTreatmentDetail("a");
        entity.setAdvice("a");
        entity.setPrice(1.0);
        entity.setStatus(1);

        PrescriptionHerbItem entity1 = new PrescriptionHerbItem();
        entity1.setId(1L);
        entity1.setPrescriptionId(1L);
        entity1.setItemId(1L);
        entity1.setChargeItemId(1L);
        entity1.setMedicalDoctorId(1L);
        entity1.setPrice(1.0);

        ArrayList<PrescriptionHerbItem> items = new ArrayList<>();
        items.add(entity1);
        entity.setItems(items);

        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("prescription", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/prescription/herb/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }
}
