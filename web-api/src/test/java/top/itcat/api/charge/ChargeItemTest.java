package top.itcat.api.charge;

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
import top.itcat.entity.charge.ChargeItem;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChargeItemTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private OrderServiceClient orderServiceClient;

    @Before
    public void setUp() throws InterruptedException, IOException, KeeperException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetChargeItem() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/charge/chargeItem/get")
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
    public void testAddChargeItem() throws Exception {
        JSONObject req = new JSONObject();
        ChargeItem entity = new ChargeItem();
        entity.setActuallyPaid(1.0);
        entity.setAmount(1);
        entity.setBillingCategoryId(1L);
        entity.setOperatorId(1L);
        entity.setProjectId(1L);
        entity.setChargeSubjectId(1L);
        entity.setDailyKnot(1);
        entity.setDepartmentId(1L);
        entity.setCreateDepartmentId(1L);
        entity.setMeasureWord("a");
        entity.setMedicalRecordNo(1L);
        entity.setName("a");
        entity.setOperationTime(new Date().getTime());
        entity.setPayable(1.0);
        entity.setSpecification("a");
        entity.setStatus(1);
        entity.setUnitPrice(1.0);
        entity.setValid(1);
        entity.setId(1L);
        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("charge_items", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/charge/chargeItem/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateChargeItem() throws Exception {
        ChargeItem entity = new ChargeItem();
        JSONObject req = new JSONObject();
        entity.setActuallyPaid(1.0);
        entity.setAmount(1);
        /* billingCategoryId=null, operatorId=null,  projectId=null,*/
        entity.setBillingCategoryId(1L);
        entity.setOperatorId(1L);
        entity.setProjectId(1L);
        entity.setChargeSubjectId(1L);
        entity.setDailyKnot(1);
        entity.setDepartmentId(1L);
        entity.setCreateDepartmentId(1L);
        entity.setMeasureWord("a");
        entity.setMedicalRecordNo(1L);
        entity.setName("a");
        entity.setOperationTime(new Date().getTime());
        entity.setPayable(1.0);
        entity.setSpecification("a");
        entity.setStatus(1);
        entity.setUnitPrice(1.0);
        entity.setValid(1);
        entity.setId(1L);

        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("charge_item", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/charge/chargeItem/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }
}
