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
import top.itcat.entity.diagnose.ApplyGroup;
import top.itcat.entity.diagnose.ApplyItemTemplate;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplyGroupTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws InterruptedException, IOException, KeeperException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testGetApplyGroup() throws Exception {
        JSONObject req = new JSONObject();
        req.put("search_key", "a");

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.post("/diagnose/apply/group/get")
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
    public void testAddApplyGroup() throws Exception {
        JSONObject req = new JSONObject();

        ApplyGroup entity = new ApplyGroup();
        entity.setId(12L);
        entity.setCode("a");
        entity.setName("a");
        entity.setCategory(1);
        entity.setDocumentCategory(1);
        entity.setServiceObject(1);
        entity.setSuitableRange(1);
        entity.setCreatorId(1L);
        entity.setDepartmentId(1L);
        entity.setCreateTime(1L);
        entity.setClinicalImpression("a");
        entity.setClinicalDiagnosis("a");
        entity.setGoalAndRequirement("a");
        entity.setRemark("a");


        ApplyItemTemplate entity1 = new ApplyItemTemplate();
        entity1.setId(24L);
        entity1.setGroupId(1L);
        entity1.setNonmedicalId(1L);
        entity1.setNote("a");
        ArrayList<ApplyItemTemplate> items = new ArrayList<>();
        items.add(entity1);
        entity.setItems(items);


        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(entityJson);
        req.put("list", jsonArray);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/apply/group/manage/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }

    @Test
    public void testUpdateApplyGroup() throws Exception {
        JSONObject req = new JSONObject();

        ApplyGroup entity = new ApplyGroup();
        entity.setId(12L);
        entity.setCode("a");
        entity.setName("a");
        entity.setCategory(1);
        entity.setDocumentCategory(1);
        entity.setServiceObject(1);
        entity.setSuitableRange(1);
        entity.setCreatorId(1L);
        entity.setDepartmentId(1L);
        entity.setCreateTime(1L);
        entity.setClinicalImpression("a");
        entity.setClinicalDiagnosis("a");
        entity.setGoalAndRequirement("a");
        entity.setRemark("a");

        ApplyItemTemplate entity1 = new ApplyItemTemplate();
        entity1.setId(24L);
        entity1.setGroupId(1L);
        entity1.setNonmedicalId(1L);
        entity1.setNote("a");
        ArrayList<ApplyItemTemplate> items = new ArrayList<>();
        items.add(entity1);
        entity.setItems(items);


        JSONObject entityJson = (JSONObject) JSONObject.toJSON(entity);
        req.put("apply_group", entityJson);

        JSONObject json = JSON.parseObject(mockMvc.perform(MockMvcRequestBuilders.
                post("/diagnose/apply/group/manage/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString());
        assertEquals(json.getJSONObject("base").get("code"), 200);
    }
}
